package com.crimsonlogic.turfmanagementsystem.serviceImpl;

import com.crimsonlogic.turfmanagementsystem.dto.PaymentDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Payment;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.repository.PaymentRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.repository.BookingRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//Author: Shradha
//payment calculation
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TurfRepository turfRepository;
//creating the payment
    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Users user = usersRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));

        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Booking ID"));

        Turf turf = turfRepository.findById(paymentDTO.getTurfId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Turf ID"));

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setBooking(booking);
        payment.setAmount(paymentDTO.getAmount());
        payment.setTransactionType(paymentDTO.getTransactionType());
        payment.setTurf(turf);

        Payment savedPayment = paymentRepository.save(payment);
        return mapToDTO(savedPayment);
    }

    // You might want to add a method to map Payment entity to PaymentDTO
    private PaymentDTO mapToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getUser().getUserId(),
                payment.getBooking().getBookingId(),
                payment.getAmount(),
                payment.getTransactionType(),
                payment.getTurf().getTurfId()
        );
    }
}
