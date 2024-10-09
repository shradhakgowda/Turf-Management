package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * BookingController handles booking-related HTTP requests.
 * Author: Shradha
 */
import com.crimsonlogic.turfmanagementsystem.dto.BookingDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    //book a slot
    @PostMapping("/bookslot")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
    	
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.ok(createdBooking);
    }

    
    //get a booking by id
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable String bookingId) {
        BookingDTO booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }
    
   
  
    //get all bookings
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllBookings() {
        List<Map<String, Object>> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

//delete booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
    
    //get booking of customer
    @GetMapping("/customerbooking")
    public ResponseEntity<List<BookingDTO>> getAllBookingsByCustomerId(@RequestParam String customerId) {
        List<BookingDTO> bookings = bookingService.getAllBookingsByCustomerId(customerId);
        return ResponseEntity.ok(bookings);
    }
    
    //cancel booking
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {
        try {
            bookingService.cancelBooking(bookingId);  // Call service method to cancel booking
            return ResponseEntity.ok("Booking cancelled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to cancel booking: " + e.getMessage());
        }
    }
    
    
    
    @GetMapping("/trainer/{trainerId}")
    public List<BookingDTO> getBookingsByTrainer(@PathVariable String trainerId) {
        return bookingService.getBookingsByTrainerId(trainerId);
    }
    
    
    @GetMapping("/book/{slotId}")
    public ResponseEntity<BookingDTO> getBookingDetails(@PathVariable String slotId) {
    	
    	System.err.println("inside booking controller");
        BookingDTO booking = bookingService.getBookingBySlotId(slotId);
        return ResponseEntity.ok(booking);// Use a DTO to send the booking response
    }
    
    
}

