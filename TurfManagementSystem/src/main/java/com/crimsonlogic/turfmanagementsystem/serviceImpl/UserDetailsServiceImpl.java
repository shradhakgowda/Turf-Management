package com.crimsonlogic.turfmanagementsystem.serviceImpl;

import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.repository.UserDetailsRepository;
import com.crimsonlogic.turfmanagementsystem.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//author:shradha
//user details service calculation
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    //create usr details
    @Override
    public UserDetailsDTO createUserDetails(UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = mapToEntity(userDetailsDTO);
        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);
        return mapToDTO(savedUserDetails);
    }

    //get all users by id
    @Override
    public UserDetailsDTO getUserDetailsById(String userId) {
        Optional<UserDetails> userDetails = userDetailsRepository.findById(userId);
        return userDetails.map(this::mapToDTO).orElse(null);
    }
//get all user details
    @Override
    public List<UserDetailsDTO> getAllUserDetails() {
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        return userDetailsList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
//update user details
    @Override
    public UserDetailsDTO updateUserDetails(String userId, UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = userDetailsRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("UserDetails not found with id: " + userId));

        userDetails.setFirstName(userDetailsDTO.getFirstName());
        userDetails.setLastName(userDetailsDTO.getLastName());
        userDetails.setPhoneNumber(userDetailsDTO.getPhoneNumber());

        UserDetails updatedUserDetails = userDetailsRepository.save(userDetails);
        return mapToDTO(updatedUserDetails);
    }
//delete user details
    @Override
    public void deleteUserDetails(String userId) {
        userDetailsRepository.deleteById(userId);
    }

    // Utility methods to map between entity and DTO
    private UserDetailsDTO mapToDTO(UserDetails userDetails) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setUserId(userDetails.getUserId());
        dto.setFirstName(userDetails.getFirstName());
        dto.setLastName(userDetails.getLastName());
        dto.setPhoneNumber(userDetails.getPhoneNumber());
        return dto;
    }

    private UserDetails mapToEntity(UserDetailsDTO dto) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(dto.getUserId());
        userDetails.setFirstName(dto.getFirstName());
        userDetails.setLastName(dto.getLastName());
        userDetails.setPhoneNumber(dto.getPhoneNumber());
        return userDetails;
    }

    //get details by trainer id
	@Override
	public UserDetailsDTO getTrainerById(String trainerId) {
		UserDetails user=userDetailsRepository.findByUser_UserId(trainerId);
		return mapToDTO(user);
	}
}
