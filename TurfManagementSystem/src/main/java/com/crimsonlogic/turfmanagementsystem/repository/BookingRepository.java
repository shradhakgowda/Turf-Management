package com.crimsonlogic.turfmanagementsystem.repository;

import com.crimsonlogic.turfmanagementsystem.dto.BookingDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Booking;
import com.crimsonlogic.turfmanagementsystem.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    
	List<Booking> findByCustomer_UserId(String customerId);// You can add custom query methods here if needed
	List<Booking> findByAssignedTrainer_UserId(String assignedTrainer);
	BookingDTO findBySlot_SlotId(String slotId);
}
