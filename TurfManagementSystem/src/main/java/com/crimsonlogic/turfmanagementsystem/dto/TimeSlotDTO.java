package com.crimsonlogic.turfmanagementsystem.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
    private String slotId;            // Custom generated slot ID with "SL" prefix
    private String turfId;            // Foreign key reference to the turf (String ID)
    private LocalTime startTime;  // Start time for the slot
    private LocalTime endTime;    // End time for the slot
    private String slotAvailability;  // Availability of the slot (e.g., Available, Booked)
    private LocalDate slotDate;       // The date for the slot
}
