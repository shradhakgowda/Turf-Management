package com.crimsonlogic.turfmanagementsystem.controller;

/**
 * ReviewController handles review-related HTTP requests.
 * Author: Shradha
 */
import com.crimsonlogic.turfmanagementsystem.dto.ReviewDTO;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Get all reviews
    @GetMapping("/getall")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        try {
            List<ReviewDTO> reviews = reviewService.getAllReviews();
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            // You can log the exception here if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get review by reviewId
    @GetMapping("/get/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable String reviewId) {
        try {
            ReviewDTO review = reviewService.getReviewById(reviewId);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (Exception e) {
            // You can log the exception here if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new review
    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO createdReview = reviewService.createReview(reviewDTO);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (Exception e) {
            // You can log the exception here if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Delete review by reviewId
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content returned
        } catch (Exception e) {
            // You can log the exception here if needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
 //get review by trainer id
    @GetMapping("/trainer/{trainerId}")
    public List<ReviewDTO> getReviewsByTrainerId(@PathVariable String trainerId) {
        return reviewService.getReviewsByTrainerId(trainerId);
    }
   //get trainer details by trainer id 
    @GetMapping("/details/{trainerId}")
    public UserDetails getTrainerDetailsByTrainerId(@PathVariable String trainerId) {
    	
    	
    	System.err.println("inside controller");
        return reviewService.getTrainerDetailsByTrainerId(trainerId);
    }
}
