package com.crimsonlogic.turfmanagementsystem.service;



import java.util.List;

import com.crimsonlogic.turfmanagementsystem.dto.RegistrationRequestDTO;
import com.crimsonlogic.turfmanagementsystem.dto.UserDTO;
import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;

public interface UsersService {
    UserDTO createUser(UserDTO usersDTO);
    public UserDTO registerUser(UserDTO userDTO, UserDetailsDTO userDetailsDTO,String roleName);
    UserDTO getUserById(String userId);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(String userId, UserDTO usersDTO);
    void deleteUser(String userId);
    public UserDTO loginUser(String email, String password) throws ResourceNotFoundException ;
    List<UserDTO> getAllTrainers();
	List<RegistrationRequestDTO> getUsersByRole(String roleName);
    

}
