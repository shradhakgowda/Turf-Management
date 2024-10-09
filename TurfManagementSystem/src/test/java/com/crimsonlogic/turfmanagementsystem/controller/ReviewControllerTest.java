package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.ReviewDTO;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    private ReviewDTO reviewDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample ReviewDTO
        reviewDTO = new ReviewDTO("1", "trainerId", "Great trainer!");
    }

    @Test
    public void testGetAllReviews() {
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(reviewDTO));

        ResponseEntity<List<ReviewDTO>> response = reviewController.getAllReviews();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Great trainer!", response.getBody().get(0).getReviewText());
    }

    @Test
    public void testGetAllReviews_Exception() {
        when(reviewService.getAllReviews()).thenThrow(new RuntimeException());

        ResponseEntity<List<ReviewDTO>> response = reviewController.getAllReviews();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetReviewById_Success() throws ResourceNotFoundException {
        when(reviewService.getReviewById("1")).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.getReviewById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Great trainer!", response.getBody().getReviewText());
    }

    @Test
    public void testGetReviewById_NotFound() throws ResourceNotFoundException {
        when(reviewService.getReviewById("1")).thenThrow(new RuntimeException());

        ResponseEntity<ReviewDTO> response = reviewController.getReviewById("1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateReview() {
        when(reviewService.createReview(any(ReviewDTO.class))).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.createReview(reviewDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Great trainer!", response.getBody().getReviewText());
    }

    @Test
    public void testCreateReview_Exception() {
        when(reviewService.createReview(any(ReviewDTO.class))).thenThrow(new RuntimeException());

        ResponseEntity<ReviewDTO> response = reviewController.createReview(reviewDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteReview_Success() throws ResourceNotFoundException {
        doNothing().when(reviewService).deleteReview("1");

        ResponseEntity<Void> response = reviewController.deleteReview("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteReview_Exception() throws ResourceNotFoundException {
        doThrow(new RuntimeException()).when(reviewService).deleteReview("1");

        ResponseEntity<Void> response = reviewController.deleteReview("1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetReviewsByTrainerId() {
        when(reviewService.getReviewsByTrainerId("trainerId")).thenReturn(Arrays.asList(reviewDTO));

        List<ReviewDTO> response = reviewController.getReviewsByTrainerId("trainerId");

        assertEquals(1, response.size());
        assertEquals("Great trainer!", response.get(0).getReviewText());
    }

    @Test
    public void testGetTrainerDetailsByTrainerId() {
        UserDetails userDetails = new UserDetails();
        when(reviewService.getTrainerDetailsByTrainerId("trainerId")).thenReturn(userDetails);

        UserDetails response = reviewController.getTrainerDetailsByTrainerId("trainerId");

        assertNotNull(response);
        verify(reviewService).getTrainerDetailsByTrainerId("trainerId");
    }
}
