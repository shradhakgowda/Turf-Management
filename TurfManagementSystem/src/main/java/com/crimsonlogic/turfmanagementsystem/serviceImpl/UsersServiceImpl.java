package com.crimsonlogic.turfmanagementsystem.serviceImpl;
//author:shradha
//user details service calculation

import com.crimsonlogic.turfmanagementsystem.dto.RegistrationRequestDTO;

import com.crimsonlogic.turfmanagementsystem.dto.UserDTO;
import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.entity.Wallet;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.RolesRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UserDetailsRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.repository.WalletRepository;
import com.crimsonlogic.turfmanagementsystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserDetailsRepository usersDetailRepository;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    @Autowired
    private WalletRepository walletRepository;
    
    
 
  //register user  
    @Override
    public UserDTO registerUser(UserDTO userDTO, UserDetailsDTO userDetailsDTO, String roleName) {
        // Validate if the user already exists
        
        
        if (usersRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Fetch or create role based on roleName
        Roles role = rolesRepository.findByRoleName(roleName)
                .orElseGet(() -> {
                    Roles newRole = new Roles();
                    newRole.setRoleName(roleName);
                    return rolesRepository.save(newRole);  // Save the new role if it doesn't exist
                });

        // Set role ID in the UserDTO after fetching/creating the role
        userDTO.setRoleId(role.getRoleId());

        // Create and save user entity
        Users user = new Users();
        user.setPassword(userDTO.getPassword());  // Consider encrypting the password
        user.setEmail(userDTO.getEmail());
        user.setRole(role);  // Set the role from fetched Roles entity
        user = usersRepository.save(user);

        // Create and save user details entity
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(userDetailsDTO.getFirstName());
        userDetails.setLastName(userDetailsDTO.getLastName());
        userDetails.setPhoneNumber(userDetailsDTO.getPhoneNumber());
        userDetails.setUser(user);  // Associate with user
        usersDetailRepository.save(userDetails);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        walletRepository.save(wallet);
        return new UserDTO(user.getUserId(), user.getPassword(), user.getEmail(), role.getRoleId());
    }
   
    //login user
    @Override
    public UserDTO loginUser(String email, String password) throws ResourceNotFoundException {
        Users user = usersRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ResourceNotFoundException("User does not exist or invalid password");
        }

        // Fetch role details
        Long roleId = user.getRole().getRoleId();
        Roles role = rolesRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        // Create UserDTO without roleName in the response
        return new UserDTO(user.getUserId(), user.getPassword(), user.getEmail(), roleId);
    }
    
    //create user id not used
    @Override
    public UserDTO createUser(UserDTO usersDTO) {
        Users user = mapToEntity(usersDTO);
        Users savedUser = usersRepository.save(user);
        return mapToDTO(savedUser);
    }

    //get user by id
    @Override
    public UserDTO getUserById(String userId) {
        Optional<Users> user = usersRepository.findById(userId);
        return user.map(this::mapToDTO).orElse(null);
    }

    //get all users
    @Override
    public List<UserDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //update user
    @Override
    public UserDTO updateUser(String userId, UserDTO usersDTO) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(usersDTO.getEmail());
        user.setPassword(usersDTO.getPassword());
        Users updatedUser = usersRepository.save(user);
        return mapToDTO(updatedUser);
    }

    //delete user
    @Override
    public void deleteUser(String userId) {
        usersRepository.deleteById(userId);
    }

    // Utility methods to map between entity and DTO
    private UserDTO mapToDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }

    private Users mapToEntity(UserDTO dto) {
        Users user = new Users();
        user.setUserId(dto.getUserId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
    @Override
    public List<UserDTO> getAllTrainers() {
        List<Users> trainers = usersRepository.findAllByRole_RoleName("Trainer");
        return trainers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    //list all the users
    @Override
    public List<RegistrationRequestDTO> getUsersByRole(String roleName) {
        List<Users> users = usersRepository.findByRole_RoleName(roleName);
        List<RegistrationRequestDTO> userDTOs = new ArrayList<>();

        for (Users user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRoleId(user.getRole() != null ? user.getRole().getRoleId() : null); // Handle null role

             // Fetch UserDetails by userId
            UserDetails userDetails = usersDetailRepository.findByUser_UserId(user.getUserId());

            UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
            if (userDetails != null) {
                userDetailsDTO.setUserId(userDetails.getUserId());
                userDetailsDTO.setFirstName(userDetails.getFirstName());
                userDetailsDTO.setLastName(userDetails.getLastName());
                userDetailsDTO.setPhoneNumber(userDetails.getPhoneNumber());
            }
            userDetailsDTO.setUser(userDTO); // Associate user DTO with user details DTO

            RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
            registrationRequestDTO.setUserDTO(userDTO);
            registrationRequestDTO.setUserDetailsDTO(userDetailsDTO);
            registrationRequestDTO.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);

            userDTOs.add(registrationRequestDTO);
        }

        return userDTOs;
    }


    
}
