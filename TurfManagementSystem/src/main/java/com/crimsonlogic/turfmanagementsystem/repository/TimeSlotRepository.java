package com.crimsonlogic.turfmanagementsystem.repository;

import com.crimsonlogic.turfmanagementsystem.entity.TimeSlot;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {
    @Modifying
    @Query("DELETE FROM TimeSlot ts WHERE ts.slotDate = ?1")
    void deleteByDate(LocalDate date);
    List<TimeSlot> findByTurf_TurfIdAndSlotDateAfterAndSlotAvailability(String turfId, LocalDate date, String slotAvailability);
    List<TimeSlot> findByTurf_TurfIdAndSlotDateAndSlotAvailability(String turfId, LocalDate slotDate, String availability);
    
}

