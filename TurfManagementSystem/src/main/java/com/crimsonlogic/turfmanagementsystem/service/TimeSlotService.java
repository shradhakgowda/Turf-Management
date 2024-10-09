package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.TimeSlotDTO;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {

    TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO);

    TimeSlotDTO getTimeSlotById(String slotId);

    List<TimeSlotDTO> getAllTimeSlots();

    TimeSlotDTO updateTimeSlot(String slotId, TimeSlotDTO timeSlotDTO);

    void deleteTimeSlot(String slotId);
    List<TimeSlotDTO> findAvailableSlotsForTurfAndDate(String turfId, LocalDate slotDate);

    
    
    void createSlotsForNewTurf(String turfId);
    public List<TimeSlotDTO> getAvailableSlotsByTurfId(String turfId);// New method to create slots for a new turf

    // Additional method if needed for scheduled deletion
    void deleteOldTimeSlots(); // Optional, if you want to expose it
}
