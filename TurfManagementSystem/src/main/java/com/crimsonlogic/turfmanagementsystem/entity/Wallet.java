package com.crimsonlogic.turfmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet {
    @Id
    @Column(name = "userwallet_id")
    private String userwalletId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "balance")
    private BigDecimal balance;

    // Getters and setters
    
    
 // Method to generate custom ID before persisting
    @PrePersist
    public void generateId() {
        this.userwalletId = CustomPrefixIdentifierGenerator.generateId("WALL");  
    }
}

