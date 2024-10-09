package com.crimsonlogic.turfmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private String reviewId;   // Custom generated review ID with "REV" prefix
    private String trainerId;     // Foreign key reference to the user (String ID) - assuming the "trainer" is actually a user
    private String reviewText; // Text of the review
         
}

