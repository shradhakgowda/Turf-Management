package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * UserDetailsController handles user details-related HTTP requests.
 * Author: Shradha
 */
import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;
import com.crimsonlogic.turfmanagementsystem.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    // Get all UserDetails
    @GetMapping("/getall")
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails() {
        try {
            List<UserDetailsDTO> userDetailsList = userDetailsService.getAllUserDetails();
            return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get UserDetails by userId
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDetailsDTO> getUserDetailsById(@PathVariable String userId) {
        try {
            UserDetailsDTO userDetails = userDetailsService.getUserDetailsById(userId);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new UserDetails entry
    @PostMapping("/create")
    public ResponseEntity<UserDetailsDTO> createUserDetails(@RequestBody UserDetailsDTO userDetailsDTO) {
        try {
            UserDetailsDTO createdUserDetails = userDetailsService.createUserDetails(userDetailsDTO);
            return new ResponseEntity<>(createdUserDetails, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Exception is caught and 500 returned
        }
    }

    // Update UserDetails by userId
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDetailsDTO> updateUserDetails(@PathVariable String userId, @RequestBody UserDetailsDTO userDetailsDTO) {
        try {
            UserDetailsDTO updatedUserDetails = userDetailsService.updateUserDetails(userId, userDetailsDTO);
            return new ResponseEntity<>(updatedUserDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Generic server error handling
        }
    }
    
    //Get user details whose role is trianer
    @GetMapping("/trainers/{trainerId}")
    public ResponseEntity<UserDetailsDTO> getTrainerDetails(@PathVariable String trainerId) {
    	
    	System.err.println("inside trainer controller");
        UserDetailsDTO trainer = userDetailsService.getTrainerById(trainerId);
        return ResponseEntity.ok(trainer);
    }
    


}
