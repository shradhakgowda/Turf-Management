package com.crimsonlogic.turfmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String paymentId;       // Custom generated payment ID with "PM" prefix
    private String userId;          // Foreign key reference to the user (String ID)
    private String bookingId;       // Foreign key reference to the booking (String ID)
    private Double amount;          // Payment amount
    private String transactionType; // Type of transaction (e.g., Credit, Debit, etc.)
    private String turfId;          // Foreign key reference to the turf (String ID)
}

