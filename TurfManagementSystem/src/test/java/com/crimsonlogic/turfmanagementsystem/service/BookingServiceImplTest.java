package com.crimsonlogic.turfmanagementsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.crimsonlogic.turfmanagementsystem.dto.BookingDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.TimeSlot;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.repository.BookingRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TimeSlotRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.BookingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

public class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TurfRepository turfRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    private Users testCustomer;
    private Turf testTurf;
    private TimeSlot testSlot;
    private Booking testBooking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testCustomer = new Users();
        testCustomer.setUserId("customer1");
        testCustomer.setEmail("customer@example.com");

        testTurf = new Turf();
        testTurf.setTurfId("turf1");
        testTurf.setTurfPricePerHour(100.00);

        testSlot = new TimeSlot();
        testSlot.setSlotId("slot1");

        testBooking = new Booking();
        testBooking.setBookingId("booking1");
        testBooking.setCustomer(testCustomer);
        testBooking.setTurf(testTurf);
        testBooking.setSlot(testSlot);
        testBooking.setTotalAmount(100.0);
        testBooking.setBookingDate(LocalDate.now());
    }

    @Test
    public void testCreateBooking() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setCustomerId(testCustomer.getUserId());
        bookingDTO.setTurfId(testTurf.getTurfId());
        bookingDTO.setSlotId(testSlot.getSlotId());

        when(usersRepository.findById(testCustomer.getUserId())).thenReturn(Optional.of(testCustomer));
        when(turfRepository.findById(testTurf.getTurfId())).thenReturn(Optional.of(testTurf));
        when(timeSlotRepository.findById(testSlot.getSlotId())).thenReturn(Optional.of(testSlot));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);

        BookingDTO savedBookingDTO = bookingService.createBooking(bookingDTO);

        assertThat(savedBookingDTO).isNotNull();
        assertThat(savedBookingDTO.getBookingId()).isEqualTo(testBooking.getBookingId());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    public void testGetBookingById() {
        when(bookingRepository.findById(testBooking.getBookingId())).thenReturn(Optional.of(testBooking));

        BookingDTO foundBookingDTO = bookingService.getBookingById(testBooking.getBookingId());

        assertThat(foundBookingDTO).isNotNull();
        assertThat(foundBookingDTO.getBookingId()).isEqualTo(testBooking.getBookingId());
    }

    @Test
    public void testGetAllBookings() {
        // You can implement this based on how you want to test getAllBookings method
    }

    @Test
    public void testDeleteBooking() {
        doNothing().when(bookingRepository).deleteById(testBooking.getBookingId());

        bookingService.deleteBooking(testBooking.getBookingId());

        verify(bookingRepository).deleteById(testBooking.getBookingId());
    }

    

    @Test
    public void testGetBookingsByTrainerId() {
        // Similar to other tests, add mock data and assertions for this method
    }

    @Test
    public void testGetBookingBySlotId() {
        when(bookingRepository.findBySlot_SlotId(testSlot.getSlotId())).thenReturn(new BookingDTO());

        BookingDTO foundBookingDTO = bookingService.getBookingBySlotId(testSlot.getSlotId());

        assertThat(foundBookingDTO).isNotNull();
    }

    // Add more tests for remaining methods and edge cases as needed
}
