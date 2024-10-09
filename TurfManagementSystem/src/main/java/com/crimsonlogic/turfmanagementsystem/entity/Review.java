package com.crimsonlogic.turfmanagementsystem.entity;



import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @Column(name = "review_id")
    private String reviewId;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Users trainer;

    @Column(name = "review_text")
    private String reviewText;

    // Assuming rating is between 1 and 5

    // Getters and setters
    @PrePersist
    public void generatePaymentId() {
        this.reviewId = CustomPrefixIdentifierGenerator.generateId("REV");
    }

	
    
    
}

