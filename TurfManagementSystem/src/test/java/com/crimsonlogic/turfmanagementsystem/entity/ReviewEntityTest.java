package com.crimsonlogic.turfmanagementsystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class ReviewEntityTest {

    private Review review;
    private Users trainerMock; // Store the mock here

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new Review();
        trainerMock = mock(Users.class); // Create the mock here
        review.setTrainer(trainerMock); // Use the same mock
        review.setReviewText("Excellent service!");
    }

    @Test
    public void testGenerateId() {
        // Mock the behavior of CustomPrefixIdentifierGenerator
        String expectedId = "REV-123"; // Assume this is what the generator would return
        mockStatic(CustomPrefixIdentifierGenerator.class);
        when(CustomPrefixIdentifierGenerator.generateId("REV")).thenReturn(expectedId);

        // Call the method to be tested
        review.generatePaymentId();

        // Verify the result
        assertEquals(expectedId, review.getReviewId());
    }

    @Test
    public void testReviewInitialization() {
        review.generatePaymentId(); // Ensure reviewId is generated

        assertEquals("Excellent service!", review.getReviewText());
        assertNotNull(review.getReviewId()); // Check that reviewId is generated and not null
        assertSame(trainerMock, review.getTrainer()); // Check against the stored mock using assertSame
    }

    // Test Getters and Setters
    @Test
    public void testGettersAndSetters() {
        // Test trainer
        Users newTrainer = mock(Users.class);
        review.setTrainer(newTrainer);
        assertSame(newTrainer, review.getTrainer()); // Use assertSame for reference equality

        // Test reviewText
        String newReviewText = "Great experience!";
        review.setReviewText(newReviewText);
        assertEquals(newReviewText, review.getReviewText());

        // Test reviewId
        String newReviewId = "REV-456";
        review.setReviewId(newReviewId);
        assertEquals(newReviewId, review.getReviewId());
    }
}
