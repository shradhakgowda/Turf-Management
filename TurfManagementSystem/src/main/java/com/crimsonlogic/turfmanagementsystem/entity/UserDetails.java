package com.crimsonlogic.turfmanagementsystem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "userd_id")
    private String userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @PrePersist
    public void generateId() {
        this.userId = CustomPrefixIdentifierGenerator.generateId("USER");  
    }

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Correct reference to Users entity
    private Users user;
}
