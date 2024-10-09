package com.crimsonlogic.turfmanagementsystem.controller;


import com.crimsonlogic.turfmanagementsystem.dto.RegistrationRequestDTO;
import com.crimsonlogic.turfmanagementsystem.dto.UserDTO;
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
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    @Mock
    private RolesRepository rolesRepository;
    
    @Mock
    private UsersRepository usersRepository;
    
    @Mock
    private UserDetailsRepository usersDetailRepository;
    
    
    @Mock
    private WalletRepository walletRepository;
    
    

    @Mock
    private HttpSession session;

    private UserDTO userDTO;
    private RegistrationRequestDTO registrationRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setUserId("1");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setRoleId(1L); // Assuming roleId is of type Long

        registrationRequest = new RegistrationRequestDTO();
        registrationRequest.setUserDTO(userDTO);
        // Set other fields as necessary
    }

    @Test
    public void testRegisterUser_Success() {
        // Create a mock UserDTO that we expect to be returned
        UserDTO expectedUser = new UserDTO();
        expectedUser.setUserId("1");
        expectedUser.setEmail("test@example.com");
        expectedUser.setPassword("password");
        expectedUser.setRoleId(1L); // Set roleId as necessary

        // Mock the behavior of usersRepository to return false for email check
        when(usersRepository.existsByEmail(expectedUser.getEmail())).thenReturn(false);
        
        // Mock the behavior of rolesRepository
        Roles role = new Roles();
        role.setRoleId(1L);
        role.setRoleName("USER");
        when(rolesRepository.findByRoleName("USER")).thenReturn(Optional.of(role));

        // Mock the save behavior of usersRepository and usersDetailRepository
        when(usersRepository.save(any(Users.class))).thenReturn(new Users()); // Mock save for Users
        when(usersDetailRepository.save(any(UserDetails.class))).thenReturn(new UserDetails()); // Mock save for UserDetails
        when(walletRepository.save(any(Wallet.class))).thenReturn(new Wallet()); // Mock save for Wallet

        // Mock the actual service method to return the expected UserDTO
        when(usersService.registerUser(any(), any(), any())).thenReturn(expectedUser);

        ResponseEntity<String> response = usersController.registerUser(registrationRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("User registered successfully!");
    }


    @Test
    public void testLogin_Success() {
        try {
			when(usersService.loginUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(userDTO);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Roles role = new Roles();
        role.setRoleId(1L);
        role.setRoleName("USER");
        when(rolesRepository.findById(userDTO.getRoleId())).thenReturn(Optional.of(role));

        ResponseEntity<Map<String, String>> response = usersController.login(userDTO, session);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("userId", userDTO.getUserId());
        assertThat(response.getBody()).containsEntry("email", userDTO.getEmail());
        assertThat(response.getBody()).containsEntry("roleName", role.getRoleName());

        // Verify session attributes are set
        verify(session).setAttribute("userId", userDTO.getUserId());
        verify(session).setAttribute("email", userDTO.getEmail());
        verify(session).setAttribute("roleName", role.getRoleName());
    }

    @Test
    public void testLogin_UserNotFound() {
        try {
			when(usersService.loginUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(null);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ResponseEntity<Map<String, String>> response = usersController.login(userDTO, session);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).containsEntry("message", "Invalid credentials");
    }

    @Test
    public void testLogin_RoleNotFound() {
        try {
			when(usersService.loginUser(userDTO.getEmail(), userDTO.getPassword())).thenReturn(userDTO);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        when(rolesRepository.findById(userDTO.getRoleId())).thenThrow(new ResourceNotFoundException("Role not found"));

        ResponseEntity<Map<String, String>> response = usersController.login(userDTO, session);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("message", "Role not found");
    }

    @Test
    public void testGetUserById_Success() {
        when(usersService.getUserById("1")).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = usersController.getUserById("1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDTO);
    }

    @Test
    public void testGetAllUsers_Success() {
        List<UserDTO> userList = Collections.singletonList(userDTO);
        when(usersService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<UserDTO>> response = usersController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(userDTO);
    }

    @Test
    public void testUpdateUser_Success() {
        when(usersService.updateUser(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = usersController.updateUser("1", userDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDTO);
    }

    @Test
    public void testGetAllTrainers_Success() {
        List<UserDTO> trainerList = Collections.singletonList(userDTO);
        when(usersService.getAllTrainers()).thenReturn(trainerList);

        ResponseEntity<List<UserDTO>> response = usersController.getAllTrainers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(userDTO);
    }

    @Test
    public void testLogout_Success() {
        ResponseEntity<String> response = usersController.logout(session);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(session).invalidate();
    }
}
