package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.PaymentDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Payment;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.repository.PaymentRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.PaymentServiceImpl;
import com.crimsonlogic.turfmanagementsystem.repository.BookingRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TurfRepository turfRepository;

    private Users testUser;
    private Booking testBooking;
    private Turf testTurf;
    private PaymentDTO testPaymentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        testUser = new Users();
        testUser.setUserId("user123");
        testUser.setEmail("customer@example.com");

        testBooking = new Booking();
        testBooking.setBookingId("booking123");

        testTurf = new Turf();
        testTurf.setTurfId("turf123");

        testPaymentDTO = new PaymentDTO();
        testPaymentDTO.setUserId(testUser.getUserId());
        testPaymentDTO.setBookingId(testBooking.getBookingId());
        testPaymentDTO.setTurfId(testTurf.getTurfId());
        testPaymentDTO.setAmount(150.0);
        testPaymentDTO.setTransactionType("Debit");
    }

    @Test
    void createPayment_ShouldReturnPaymentDTO_WhenPaymentIsCreatedSuccessfully() {
        // Arrange
        when(usersRepository.findById(testUser.getUserId())).thenReturn(Optional.of(testUser));
        when(bookingRepository.findById(testBooking.getBookingId())).thenReturn(Optional.of(testBooking));
        when(turfRepository.findById(testTurf.getTurfId())).thenReturn(Optional.of(testTurf));
        
        Payment savedPayment = new Payment();
        savedPayment.setPaymentId("payment123");
        savedPayment.setUser(testUser);
        savedPayment.setBooking(testBooking);
        savedPayment.setAmount(testPaymentDTO.getAmount());
        savedPayment.setTransactionType(testPaymentDTO.getTransactionType());
        savedPayment.setTurf(testTurf);
        
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        // Act
        PaymentDTO result = paymentService.createPayment(testPaymentDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPaymentId()).isEqualTo(savedPayment.getPaymentId());
        assertThat(result.getUserId()).isEqualTo(testUser.getUserId());
        assertThat(result.getBookingId()).isEqualTo(testBooking.getBookingId());
        assertThat(result.getAmount()).isEqualTo(testPaymentDTO.getAmount());
        assertThat(result.getTransactionType()).isEqualTo(testPaymentDTO.getTransactionType());
        assertThat(result.getTurfId()).isEqualTo(testTurf.getTurfId());
    }

    @Test
    void createPayment_ShouldThrowException_WhenUserDoesNotExist() {
        // Arrange
        when(usersRepository.findById(testUser.getUserId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = 
            assertThrows(IllegalArgumentException.class, () -> paymentService.createPayment(testPaymentDTO));
        assertThat(thrown.getMessage()).isEqualTo("Invalid User ID");
    }

    @Test
    void createPayment_ShouldThrowException_WhenBookingDoesNotExist() {
        // Arrange
        when(usersRepository.findById(testUser.getUserId())).thenReturn(Optional.of(testUser));
        when(bookingRepository.findById(testBooking.getBookingId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = 
            assertThrows(IllegalArgumentException.class, () -> paymentService.createPayment(testPaymentDTO));
        assertThat(thrown.getMessage()).isEqualTo("Invalid Booking ID");
    }

    @Test
    void createPayment_ShouldThrowException_WhenTurfDoesNotExist() {
        // Arrange
        when(usersRepository.findById(testUser.getUserId())).thenReturn(Optional.of(testUser));
        when(bookingRepository.findById(testBooking.getBookingId())).thenReturn(Optional.of(testBooking));
        when(turfRepository.findById(testTurf.getTurfId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = 
            assertThrows(IllegalArgumentException.class, () -> paymentService.createPayment(testPaymentDTO));
        assertThat(thrown.getMessage()).isEqualTo("Invalid Turf ID");
    }
}
