package com.crimsonlogic.turfmanagementsystem.entity;


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
@Table(name = "bookings")
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Users customer;

    @ManyToOne
    @JoinColumn(name = "assigned_trainer")
    private Users assignedTrainer;

    @ManyToOne
    @JoinColumn(name = "turf_id", nullable = false)
    private Turf turf;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private TimeSlot slot;

    @Column(name = "booking_date", nullable = false)
    private java.time.LocalDate bookingDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    // Method to generate custom ID before persisting
    @PrePersist
    public void generateId() {
        this.bookingId = CustomPrefixIdentifierGenerator.generateId("BK");  // Using "BK" as the prefix for Booking
    }

	
}
