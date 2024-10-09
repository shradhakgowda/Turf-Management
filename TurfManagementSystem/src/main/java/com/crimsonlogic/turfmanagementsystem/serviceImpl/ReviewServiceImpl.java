package com.crimsonlogic.turfmanagementsystem.serviceImpl;
//author:shradha
//review calculation
import com.crimsonlogic.turfmanagementsystem.dto.ReviewDTO;

import com.crimsonlogic.turfmanagementsystem.entity.Review;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.repository.ReviewRepository; // Assuming you have a ReviewRepository
import com.crimsonlogic.turfmanagementsystem.repository.UserDetailsRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.service.ReviewService;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException; // Assuming you have a ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    // Inject the repository

    @Autowired
    private UsersRepository userRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepo;
    
    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //get review by id
    @Override
    public ReviewDTO getReviewById(String reviewId) throws ResourceNotFoundException {
        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
            return convertToDTO(review);
        } catch (Exception e) {
            // Handle any other general exceptions that might occur
            throw new RuntimeException("An error occurred while fetching the review", e);
        }
    }

    //create a review
    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

//delete review not used
    @Override
    public void deleteReview(String reviewId) {
        Review review;
		try {
			review = reviewRepository.findById(reviewId)
			        .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
			reviewRepository.delete(review);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    // Convert Review entity to ReviewDTO
    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getTrainer().getUserId(), // Assuming Trainer is a User entity with userId
                review.getReviewText()
        );
    }

    // Convert ReviewDTO to Review entity
    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setReviewText(reviewDTO.getReviewText());
        Users trainer_id=userRepository.findById(reviewDTO.getTrainerId()).orElse(null);
        review.setTrainer(trainer_id);// Assuming Trainer is a User entity
        return review;
    }
    
    
    //get reviews by trainer id
    @Override
    public List<ReviewDTO> getReviewsByTrainerId(String trainerId) {
        List<Review> reviews = reviewRepository.findByTrainer_UserId(trainerId); // Fetch reviews based on trainer ID
        return reviews.stream()
                .map(this::convertToDTO) // Convert Review entity to ReviewDTO
                .collect(Collectors.toList());
    }
    
    //get trainer details by trainer id
    @Override
    public UserDetails getTrainerDetailsByTrainerId(String trainerId) {
        return userDetailsRepo.findByUser_UserId(trainerId); // Fetch user details by trainerId
    }
}
