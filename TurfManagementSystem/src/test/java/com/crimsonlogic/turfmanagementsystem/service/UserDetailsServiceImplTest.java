package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.UserDetailsDTO;
import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.repository.UserDetailsRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    private UserDetailsDTO userDetailsDTO;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserId("user-1");
        userDetailsDTO.setFirstName("John");
        userDetailsDTO.setLastName("Doe");
        userDetailsDTO.setPhoneNumber("1234567890");

        userDetails = new UserDetails();
        userDetails.setUserId("user-1");
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setPhoneNumber("1234567890");
    }

    @Test
    public void createUserDetails_ShouldSaveAndReturnUserDetailsDTO() {
        when(userDetailsRepository.save(any(UserDetails.class))).thenReturn(userDetails);

        UserDetailsDTO savedUserDetailsDTO = userDetailsService.createUserDetails(userDetailsDTO);

        assertThat(savedUserDetailsDTO).isNotNull();
        assertThat(savedUserDetailsDTO.getUserId()).isEqualTo("user-1");
        verify(userDetailsRepository, times(1)).save(any(UserDetails.class));
    }

    @Test
    public void getUserDetailsById_ShouldReturnUserDetailsDTO() {
        when(userDetailsRepository.findById("user-1")).thenReturn(Optional.of(userDetails));

        UserDetailsDTO foundUserDetailsDTO = userDetailsService.getUserDetailsById("user-1");

        assertThat(foundUserDetailsDTO).isNotNull();
        assertThat(foundUserDetailsDTO.getUserId()).isEqualTo("user-1");
    }

    @Test
    public void getUserDetailsById_ShouldReturnNullIfNotFound() {
        when(userDetailsRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        UserDetailsDTO foundUserDetailsDTO = userDetailsService.getUserDetailsById("non-existent-id");

        assertThat(foundUserDetailsDTO).isNull();
    }

    @Test
    public void getAllUserDetails_ShouldReturnListOfUserDetailsDTO() {
        when(userDetailsRepository.findAll()).thenReturn(Arrays.asList(userDetails));

        List<UserDetailsDTO> userDetailsList = userDetailsService.getAllUserDetails();

        assertThat(userDetailsList).isNotEmpty();
        assertThat(userDetailsList.size()).isEqualTo(1);
    }

    @Test
    public void updateUserDetails_ShouldUpdateAndReturnUserDetailsDTO() {
        when(userDetailsRepository.findById("user-1")).thenReturn(Optional.of(userDetails));
        when(userDetailsRepository.save(any(UserDetails.class))).thenReturn(userDetails);

        UserDetailsDTO updatedUserDetailsDTO = userDetailsService.updateUserDetails("user-1", userDetailsDTO);

        assertThat(updatedUserDetailsDTO).isNotNull();
        assertThat(updatedUserDetailsDTO.getFirstName()).isEqualTo("John");
        verify(userDetailsRepository, times(1)).save(any(UserDetails.class));
    }

    @Test
    public void updateUserDetails_ShouldThrowExceptionIfNotFound() {
        when(userDetailsRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            userDetailsService.updateUserDetails("non-existent-id", userDetailsDTO);
        });

        assertThat(exception.getMessage()).contains("UserDetails not found with id: non-existent-id");
    }

    @Test
    public void deleteUserDetails_ShouldCallDeleteMethod() {
        userDetailsService.deleteUserDetails("user-1");
        verify(userDetailsRepository, times(1)).deleteById("user-1");
    }

    @Test
    public void getTrainerById_ShouldReturnUserDetailsDTO() {
        when(userDetailsRepository.findByUser_UserId("user-1")).thenReturn(userDetails);

        UserDetailsDTO foundTrainerDetailsDTO = userDetailsService.getTrainerById("user-1");

        assertThat(foundTrainerDetailsDTO).isNotNull();
        assertThat(foundTrainerDetailsDTO.getUserId()).isEqualTo("user-1");
    }
}
