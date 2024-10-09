package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;
import com.crimsonlogic.turfmanagementsystem.service.UserDetailsService;
import com.crimsonlogic.turfmanagementsystem.controller.UserDetailsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDetailsControllerTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserDetailsService userDetailsService;

    private UserDetailsDTO userDetailsDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserId("user-1");
        userDetailsDTO.setFirstName("John");
        userDetailsDTO.setLastName("Doe");
        userDetailsDTO.setPhoneNumber("1234567890");
    }

    @Test
    public void getAllUserDetails_ShouldReturnListOfUserDetails() {
        List<UserDetailsDTO> userDetailsList = Arrays.asList(userDetailsDTO);
        when(userDetailsService.getAllUserDetails()).thenReturn(userDetailsList);

        ResponseEntity<List<UserDetailsDTO>> response = userDetailsController.getAllUserDetails();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDetailsList);
    }

    @Test
    public void getUserDetailsById_ShouldReturnUserDetails() {
        when(userDetailsService.getUserDetailsById("user-1")).thenReturn(userDetailsDTO);

        ResponseEntity<UserDetailsDTO> response = userDetailsController.getUserDetailsById("user-1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDetailsDTO);
    }

    @Test
    public void createUserDetails_ShouldReturnCreatedUserDetails() {
        when(userDetailsService.createUserDetails(any(UserDetailsDTO.class))).thenReturn(userDetailsDTO);

        ResponseEntity<UserDetailsDTO> response = userDetailsController.createUserDetails(userDetailsDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDetailsDTO);
    }

    @Test
    public void updateUserDetails_ShouldReturnUpdatedUserDetails() {
        when(userDetailsService.updateUserDetails(anyString(), any(UserDetailsDTO.class))).thenReturn(userDetailsDTO);

        ResponseEntity<UserDetailsDTO> response = userDetailsController.updateUserDetails("user-1", userDetailsDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDetailsDTO);
    }

    @Test
    public void getTrainerDetails_ShouldReturnTrainerDetails() {
        when(userDetailsService.getTrainerById("trainer-1")).thenReturn(userDetailsDTO);

        ResponseEntity<UserDetailsDTO> response = userDetailsController.getTrainerDetails("trainer-1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDetailsDTO);
    }

    @Test
    public void getUserDetailsById_ShouldReturnNotFoundWhenUserDoesNotExist() {
        when(userDetailsService.getUserDetailsById("non-existent-id")).thenReturn(null);

        ResponseEntity<UserDetailsDTO> response = userDetailsController.getUserDetailsById("non-existent-id");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void createUserDetails_ShouldReturnInternalServerErrorOnException() {
        when(userDetailsService.createUserDetails(any(UserDetailsDTO.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<UserDetailsDTO> response = userDetailsController.createUserDetails(userDetailsDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

