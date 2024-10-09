package com.crimsonlogic.turfmanagementsystem.service;


import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;

import java.util.List;

public interface UserDetailsService {
    UserDetailsDTO createUserDetails(UserDetailsDTO userDetailsDTO);
    UserDetailsDTO getUserDetailsById(String userId);
    List<UserDetailsDTO> getAllUserDetails();
    UserDetailsDTO updateUserDetails(String userId, UserDetailsDTO userDetailsDTO);
    void deleteUserDetails(String userId);
	UserDetailsDTO getTrainerById(String trainerId);
}
