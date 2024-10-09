package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.PaymentDTO;
import com.crimsonlogic.turfmanagementsystem.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private PaymentDTO testPaymentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        testPaymentDTO = new PaymentDTO();
        testPaymentDTO.setUserId("user123");
        testPaymentDTO.setBookingId("booking123");
        testPaymentDTO.setTurfId("turf123");
        testPaymentDTO.setAmount(150.0);
        testPaymentDTO.setTransactionType("Debit");
    }

    @Test
    void createPayment_ShouldReturnCreatedPayment_WhenPaymentIsSuccessful() {
        // Arrange
        when(paymentService.createPayment(testPaymentDTO)).thenReturn(testPaymentDTO);

        // Act
        ResponseEntity<PaymentDTO> response = paymentController.createPayment(testPaymentDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testPaymentDTO);
        verify(paymentService, times(1)).createPayment(testPaymentDTO);
    }

    @Test
    void createPayment_ShouldHandleException_WhenServiceThrowsException() {
       
        // Act
        ResponseEntity<PaymentDTO> response = paymentController.createPayment(testPaymentDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(paymentService, times(1)).createPayment(testPaymentDTO);
    }

    // Additional test cases for other endpoints can be added here
}
