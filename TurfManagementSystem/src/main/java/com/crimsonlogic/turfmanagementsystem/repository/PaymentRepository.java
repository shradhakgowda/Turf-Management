package com.crimsonlogic.turfmanagementsystem.repository;

import com.crimsonlogic.turfmanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    // Additional query methods can be defined here if needed
}

