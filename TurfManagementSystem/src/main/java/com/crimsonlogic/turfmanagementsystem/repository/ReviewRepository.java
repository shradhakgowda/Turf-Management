package com.crimsonlogic.turfmanagementsystem.repository;

import com.crimsonlogic.turfmanagementsystem.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    // Custom query to get reviews for a specific trainer (user)
    List<Review> findByTrainer_UserId(String userId);
    
}

