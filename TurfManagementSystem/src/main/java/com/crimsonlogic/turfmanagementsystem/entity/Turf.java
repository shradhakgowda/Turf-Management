package com.crimsonlogic.turfmanagementsystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "turf")
public class Turf {

    @Id
    @Column(name = "turf_id")
    private String turfId;

    @Column(name = "turf_name", nullable = false)
    private String turfName;

    @Column(name = "turf_information")
    private String turfInformation;

    @Column(name = "turf_price_per_hour", nullable = false)
    private Double turfPricePerHour;

    @Column(name = "turf_image")
    private String turfImage;

    
    @Column(name="turf_availabality")
    private String turfAvailabality;
    // Method to generate custom ID before persisting
    @PrePersist
    public void generateId() {
        this.turfId = CustomPrefixIdentifierGenerator.generateId("TF");  // Using "TF" as the prefix for Turf
    }
}
