package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.ReviewDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Review;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.ReviewRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UserDetailsRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.ReviewServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UsersRepository userRepository;

    @Mock
    private UserDetailsRepository userDetailsRepo;

    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample Review and ReviewDTO
        review = new Review();
        review.setReviewId("1");
        review.setReviewText("Great trainer!");
        Users trainer = new Users();
        trainer.setUserId("trainerId");
        review.setTrainer(trainer);

        reviewDTO = new ReviewDTO("1", "trainerId", "Great trainer!");
    }

    @Test
    public void testGetAllReviews() {
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(review));

        List<ReviewDTO> reviews = reviewService.getAllReviews();

        assertEquals(1, reviews.size());
        assertEquals("Great trainer!", reviews.get(0).getReviewText());
    }

    @Test
    public void testGetReviewById_Success() throws ResourceNotFoundException {
        when(reviewRepository.findById("1")).thenReturn(Optional.of(review));

        ReviewDTO result = reviewService.getReviewById("1");

        assertEquals("Great trainer!", result.getReviewText());
    }

    @Test
    public void testGetReviewById_NotFound() {
        when(reviewRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reviewService.getReviewById("1");
        });
    }

    @Test
    public void testCreateReview() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertEquals("Great trainer!", result.getReviewText());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    public void testDeleteReview_Success() {
        when(reviewRepository.findById("1")).thenReturn(Optional.of(review));

        reviewService.deleteReview("1");

        verify(reviewRepository).delete(review);
    }


    @Test
    public void testGetReviewsByTrainerId() {
        when(reviewRepository.findByTrainer_UserId("trainerId")).thenReturn(Arrays.asList(review));

        List<ReviewDTO> reviews = reviewService.getReviewsByTrainerId("trainerId");

        assertEquals(1, reviews.size());
        assertEquals("Great trainer!", reviews.get(0).getReviewText());
    }

    @Test
    public void testGetTrainerDetailsByTrainerId() {
        UserDetails userDetails = new UserDetails();
        when(userDetailsRepo.findByUser_UserId("trainerId")).thenReturn(userDetails);

        UserDetails result = reviewService.getTrainerDetailsByTrainerId("trainerId");

        assertNotNull(result);
        verify(userDetailsRepo).findByUser_UserId("trainerId");
    }
}
