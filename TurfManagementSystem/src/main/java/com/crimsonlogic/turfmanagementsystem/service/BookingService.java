package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.BookingDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getBookingById(String bookingId);
    List<Map<String, Object>> getAllBookings();
    void deleteBooking(String bookingId);
    List<BookingDTO> getAllBookingsByCustomerId(String customerId);
    void cancelBooking(String bookingId) throws ResourceNotFoundException;
    List<BookingDTO> getBookingsByTrainerId(String trainerId);
	BookingDTO getBookingBySlotId(String slotId);
    
  
}
