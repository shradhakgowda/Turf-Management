package com.crimsonlogic.turfmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String bookingId;        // Custom generated booking ID with "BK" prefix
    private String customerId;       // Foreign key reference to the customer (String ID)
    private String assignedTrainerId; // Foreign key reference to the assigned trainer (String ID)
    private String turfId;           // Foreign key reference to the turf (String ID)
    private String slotId;           // Foreign key reference to the slot (String ID)
    private LocalDate bookingDate;   // Booking date
    private String status;           // Status of the booking (e.g., Confirmed, Pending, Canceled)
    private Double totalAmount;      // Total amount for the booking
}

