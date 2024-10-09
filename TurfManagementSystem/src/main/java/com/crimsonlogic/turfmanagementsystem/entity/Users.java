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
@Table(name = "users")  
public class Users {
    
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne 
    @JoinColumn(name = "role_id") 
    private Roles role; 

    @PrePersist
    public void generateId() {
        this.userId = CustomPrefixIdentifierGenerator.generateId("US");  
    }
}
