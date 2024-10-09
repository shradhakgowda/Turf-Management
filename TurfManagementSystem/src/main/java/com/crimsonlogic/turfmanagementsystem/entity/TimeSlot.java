package com.crimsonlogic.turfmanagementsystem.entity;
import java.time.LocalDate;

import java.time.LocalTime;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @Column(name = "slot_id")
    private String slotId;

    @ManyToOne
    @JoinColumn(name = "turf_id", nullable = false)
    private Turf turf;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;  // Java 8 LocalTime for time

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;    // Java 8 LocalTime for time

    @Column(name = "slot_availablility", nullable = false)
    private String slotAvailability;

    @Column(name = "slot_date", nullable = false)
    private LocalDate slotDate;   // Java 8 LocalDate for date

    // Method to generate custom ID before persisting
    @PrePersist
    public void generateId() {
        this.slotId = CustomPrefixIdentifierGenerator.generateId("SL");  // Using "SL" as the prefix for Slot
    }
}
