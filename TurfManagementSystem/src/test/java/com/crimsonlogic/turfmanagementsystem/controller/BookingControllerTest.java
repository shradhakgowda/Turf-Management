package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.BookingDTO;
import com.crimsonlogic.turfmanagementsystem.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    private BookingDTO testBookingDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        testBookingDTO = new BookingDTO();
        testBookingDTO.setBookingId("booking1");
        testBookingDTO.setCustomerId("customer1");
        testBookingDTO.setTurfId("turf1");
        testBookingDTO.setSlotId("slot1");
        testBookingDTO.setStatus("Confirmed");
        testBookingDTO.setTotalAmount(100.0);
    }

    @Test
    public void testCreateBooking() {
        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(testBookingDTO);

        ResponseEntity<BookingDTO> response = bookingController.createBooking(testBookingDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testBookingDTO);
        verify(bookingService).createBooking(testBookingDTO);
    }

    @Test
    public void testGetBookingById() {
        when(bookingService.getBookingById("booking1")).thenReturn(testBookingDTO);

        ResponseEntity<BookingDTO> response = bookingController.getBookingById("booking1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testBookingDTO);
        verify(bookingService).getBookingById("booking1");
    }

    @Test
    public void testGetAllBookings() {
        List<Map<String, Object>> bookingsList = new ArrayList<>();
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("bookingId", "booking1");
        bookingsList.add(bookingData);

        when(bookingService.getAllBookings()).thenReturn(bookingsList);

        ResponseEntity<List<Map<String, Object>>> response = bookingController.getAllBookings();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(bookingsList);
        verify(bookingService).getAllBookings();
    }

    @Test
    public void testDeleteBooking() {
        doNothing().when(bookingService).deleteBooking("booking1");

        ResponseEntity<Void> response = bookingController.deleteBooking("booking1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(bookingService).deleteBooking("booking1");
    }

    @Test
    public void testGetAllBookingsByCustomerId() {
        List<BookingDTO> customerBookings = Collections.singletonList(testBookingDTO);
        when(bookingService.getAllBookingsByCustomerId("customer1")).thenReturn(customerBookings);

        ResponseEntity<List<BookingDTO>> response = bookingController.getAllBookingsByCustomerId("customer1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(customerBookings);
        verify(bookingService).getAllBookingsByCustomerId("customer1");
    }

 

    @Test
    public void testGetBookingDetails() {
        when(bookingService.getBookingBySlotId("slot1")).thenReturn(testBookingDTO);

        ResponseEntity<BookingDTO> response = bookingController.getBookingDetails("slot1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testBookingDTO);
        verify(bookingService).getBookingBySlotId("slot1");
    }
}
