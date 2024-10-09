package com.crimsonlogic.turfmanagementsystem.serviceImpl;

import com.crimsonlogic.turfmanagementsystem.dto.BookingDTO;


import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.TimeSlot;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.entity.Wallet;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.BookingRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TimeSlotRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.repository.WalletRepository;
import com.crimsonlogic.turfmanagementsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
//Author: Shradha
//booking service calculation

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private TurfRepository turfRepository;

	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@Autowired
	private WalletRepository walletRepository;

	//create a booking
	@Override
	public BookingDTO createBooking(BookingDTO bookingDTO) {
		// Fetch the turf to get the total amount
		Users customer = usersRepository.findById(bookingDTO.getCustomerId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID"));

		Turf turf = turfRepository.findById(bookingDTO.getTurfId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid Turf ID"));

		// Fetch the trainer if assigned
		Users assignedTrainer = null;
		if (bookingDTO.getAssignedTrainerId() != null) {
			assignedTrainer = usersRepository.findById(bookingDTO.getAssignedTrainerId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid Trainer ID"));
		}

		TimeSlot slot = timeSlotRepository.findById(bookingDTO.getSlotId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid Slot ID"));
		slot.setSlotAvailability("Not Available");
		timeSlotRepository.save(slot);
		Booking booking = new Booking();
		booking.setTurf(turf);
		booking.setSlot(slot);
		booking.setCustomer(customer);
		booking.setBookingDate(LocalDate.now());
		booking.setAssignedTrainer(assignedTrainer);
		booking.setStatus("Confirmed");
		booking.setTotalAmount(turf.getTurfPricePerHour());
		Booking savedBooking = bookingRepository.save(booking);
		return mapToDTO(savedBooking);
	}

	//get booking by id
	@Override
	public BookingDTO getBookingById(String bookingId) {
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		return booking.map(this::mapToDTO).orElse(null);
	}

	//get all the bookings and fetching details from other table based on the bookings
	@Override
	public List<Map<String, Object>> getAllBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		List<Map<String, Object>> result = new ArrayList<>();

		for (Booking booking : bookings) {
			Map<String, Object> bookingData = new HashMap<>();

			// Get Booking Details
			bookingData.put("bookingId", booking.getBookingId());
			bookingData.put("totalAmount", booking.getTotalAmount());
			bookingData.put("status", booking.getStatus());

			// Get Turf Details
			Turf turf = turfRepository.findById(booking.getTurf().getTurfId()).orElse(null);
			if (turf != null) {
				bookingData.put("turfName", turf.getTurfName());
				bookingData.put("turfPrice", turf.getTurfPricePerHour());
			}

			// Get Slot Details
			TimeSlot timeSlot = timeSlotRepository.findById(booking.getSlot().getSlotId()).orElse(null);
			if (timeSlot != null) {
				bookingData.put("startTime", timeSlot.getStartTime());
				bookingData.put("endTime", timeSlot.getEndTime());
				bookingData.put("slotDate", timeSlot.getSlotDate());
			}

			// Get User Details (Customer)
			// UserDetails
			// userd=userDetailsRepo.findByUser_UserId(booking.getCustomer().getUserId());
			// System.err.println(userd);
			Users user = usersRepository.findById(booking.getCustomer().getUserId()).orElse(null);
			bookingData.put("userId", user.getUserId());
			// bookingData.put("customerName", userd.getFirstName());
			bookingData.put("customerEmail", user.getEmail());

			// Add to result list
			result.add(bookingData);
		}

		return result;
	}

	//get customer bookings
	@Override
	public List<BookingDTO> getAllBookingsByCustomerId(String customerId) {
		List<Booking> bookings = bookingRepository.findByCustomer_UserId(customerId);
		return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	//delete bookings
	@Override
	public void deleteBooking(String bookingId) {
		bookingRepository.deleteById(bookingId);
	}

	//cancel bookings
	@Override
	public void cancelBooking(String bookingId) throws ResourceNotFoundException {
		// 1. Fetch the booking details by booking ID
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

		// 2. Set the booking status to 'Rejected'
		booking.setStatus("Rejected");

		// 3. Fetch the slot associated with the booking and set it as available
		TimeSlot timeSlot = booking.getSlot();
		if (timeSlot != null) {
			timeSlot.setSlotAvailability("available");
			timeSlotRepository.save(timeSlot); // Save updated slot status
		}

		// 4. Refund the amount to the customer's wallet
		Wallet wallet = walletRepository.findByUser(booking.getCustomer())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Wallet not found for customer with ID: " + booking.getCustomer().getUserId()));

		BigDecimal refundAmount = new BigDecimal(booking.getTotalAmount());
		wallet.setBalance(wallet.getBalance().add(refundAmount)); // Add the refunded amount to the wallet balance

		// Save updated wallet balance
		walletRepository.save(wallet);

		// 5. Save the updated booking status
		bookingRepository.save(booking);
	}

	// Utility methods to map between entity and DTO
	private BookingDTO mapToDTO(Booking booking) {
		BookingDTO dto = new BookingDTO();
		dto.setBookingId(booking.getBookingId());
		dto.setCustomerId(booking.getCustomer().getUserId());
		dto.setAssignedTrainerId(
				booking.getAssignedTrainer() != null ? booking.getAssignedTrainer().getUserId() : null);
		dto.setTurfId(booking.getTurf().getTurfId());
		dto.setSlotId(booking.getSlot().getSlotId());
		dto.setBookingDate(booking.getBookingDate());
		dto.setStatus(booking.getStatus());
		dto.setTotalAmount(booking.getTotalAmount());
		return dto;
	}
//utility method
	private Booking mapToEntity(BookingDTO dto) {
		Booking booking = new Booking();
		booking.setBookingId(dto.getBookingId());
		booking.setBookingDate(dto.getBookingDate());
		booking.setStatus(dto.getStatus());
		booking.setTotalAmount(dto.getTotalAmount());

		Users customer = usersRepository.findById(dto.getCustomerId()).orElse(null);
		booking.setCustomer(customer);

		Users assignedTrainer = usersRepository.findById(dto.getAssignedTrainerId()).orElse(null);
		booking.setAssignedTrainer(assignedTrainer);

		Turf turf = turfRepository.findById(dto.getTurfId()).orElse(null);
		booking.setTurf(turf);

		TimeSlot slot = timeSlotRepository.findById(dto.getSlotId()).orElse(null);
		booking.setSlot(slot);

		return booking;
	}

	//get bookings by trainer id
	@Override
	public List<BookingDTO> getBookingsByTrainerId(String trainerId) {

		List<Booking> bookings = bookingRepository.findByAssignedTrainer_UserId(trainerId);
		
		

		return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	//get booking by slot id
	@Override
	public BookingDTO getBookingBySlotId(String slotId)
	{
		BookingDTO booking=bookingRepository.findBySlot_SlotId(slotId);
		return  booking;
	}
	
	//get bookings by trainer id
	public List<Map<String, Object>> getBookingByTrainerId1(String trainerId)
	{
		List<Booking> bookings = bookingRepository.findByAssignedTrainer_UserId(trainerId);
		List<Map<String, Object>> result = new ArrayList<>();
		for (Booking booking : bookings) {
			Map<String, Object> bookingData = new HashMap<>();

			// Get Booking Details
			bookingData.put("bookingId", booking.getBookingId());
			bookingData.put("totalAmount", booking.getTotalAmount());
			bookingData.put("status", booking.getStatus());
	
			// Get Turf Details
			Turf turf = turfRepository.findById(booking.getTurf().getTurfId()).orElse(null);
			if (turf != null) {
				bookingData.put("turfName", turf.getTurfName());
				bookingData.put("turfPrice", turf.getTurfPricePerHour());
			}
			
			// Get Slot Details
			TimeSlot timeSlot = timeSlotRepository.findById(booking.getSlot().getSlotId()).orElse(null);
			if (timeSlot != null) {
				bookingData.put("startTime", timeSlot.getStartTime());
				bookingData.put("endTime", timeSlot.getEndTime());
				bookingData.put("slotDate", timeSlot.getSlotDate());
				
			}
			
			Users user = usersRepository.findById(booking.getCustomer().getUserId()).orElse(null);
			bookingData.put("userId", user.getUserId());
			// bookingData.put("customerName", userd.getFirstName());
			bookingData.put("customerEmail", user.getEmail());

			// Add to result list
			result.add(bookingData);
		}
			return result;
		
	}
	
	
	
}
