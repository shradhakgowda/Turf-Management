package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * UsersController handles user-related HTTP requests such as registration, login, 
 * fetching user details, and managing user roles.
 * Author: Shradha
 */

import com.crimsonlogic.turfmanagementsystem.dto.RegistrationRequestDTO;

import com.crimsonlogic.turfmanagementsystem.dto.UserDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.RolesRepository;
import com.crimsonlogic.turfmanagementsystem.service.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/vi/users")
public class UsersController {

	
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    
    //register user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequestDTO registrationRequest) {
        try {
            usersService.registerUser(
                registrationRequest.getUserDTO(),
                registrationRequest.getUserDetailsDTO(),
                registrationRequest.getRoleName()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    
    //login user
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userDTO, HttpSession session) {
        try {
            // Validate user credentials (email and password)
            UserDTO user = usersService.loginUser(userDTO.getEmail(), userDTO.getPassword());
            if (user == null) {
                // Return a 401 Unauthorized if user is not found
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("message", "Invalid credentials"));
            }

            // Fetch role name using role ID from the user details
            Roles role = rolesRepository.findById(user.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

            // Store user data in the session
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("roleName", role.getRoleName());

            // Prepare the response
            Map<String, String> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("email", user.getEmail());
            response.put("roleName", role.getRoleName());

            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle specific error when the role is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // Handle all other generic errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An error occurred while logging in"));
        }
    }

    //get user by id

    @GetMapping("/getuserbyid/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
        UserDTO user = usersService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    //get all users
    @GetMapping("getallusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //update users
    @PutMapping("/updateusers/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userId, @RequestBody UserDTO usersDTO) {
        UserDTO updatedUser = usersService.updateUser(userId, usersDTO);
        return ResponseEntity.ok(updatedUser);
    }
    
    //get all  trainers
    @GetMapping("/trainers")
    public ResponseEntity<List<UserDTO>> getAllTrainers() {
        List<UserDTO> trainers = usersService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }
    
   //logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Invalidate the session to log the user out
    	System.err.println("Insider logout logic");
        session.invalidate();
        return ResponseEntity.noContent().build();
    }

    
    //fetch user details based on role name
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<RegistrationRequestDTO>> getUsersByRole(@PathVariable String roleName) {
        List<RegistrationRequestDTO> users = usersService.getUsersByRole(roleName);
        return ResponseEntity.ok(users);
    }
    
 
    
    

    
    

 
}
