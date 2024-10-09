package com.crimsonlogic.turfmanagementsystem.service;


import com.crimsonlogic.turfmanagementsystem.dto.ReviewDTO;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(String reviewId) throws ResourceNotFoundException;

    ReviewDTO createReview(ReviewDTO reviewDTO);


    void deleteReview(String reviewId) throws ResourceNotFoundException;

	List<ReviewDTO> getReviewsByTrainerId(String trainerId);

	UserDetails getTrainerDetailsByTrainerId(String trainerId);

    
    
}
