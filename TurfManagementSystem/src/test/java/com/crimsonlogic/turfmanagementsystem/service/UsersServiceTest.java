package com.crimsonlogic.turfmanagementsystem.service;


import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.crimsonlogic.turfmanagementsystem.serviceImpl.UsersServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class UsersServiceTest {

    @InjectMocks
    private UsersServiceImpl usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UserDetailsRepository usersDetailRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private WalletRepository walletRepository;

    private Users user;
    private UserDTO userDTO;
    private UserDetailsDTO userDetailsDTO;
    private Roles role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Users();
        user.setUserId("1");
        user.setEmail("test@example.com");
        user.setPassword("securePassword");

        userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());

        userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setFirstName("First");
        userDetailsDTO.setLastName("Last");
        userDetailsDTO.setPhoneNumber("1234567890");

        role = new Roles();
        role.setRoleId(1L);
        role.setRoleName("User");
    }

    @Test
    void testRegisterUser() {
        when(usersRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(rolesRepository.findByRoleName(anyString())).thenReturn(Optional.of(role));
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        when(usersDetailRepository.save(any(UserDetails.class))).thenReturn(new UserDetails());
        when(walletRepository.save(any(Wallet.class))).thenReturn(new Wallet());

        UserDTO registeredUser = usersService.registerUser(userDTO, userDetailsDTO, "User");

        assertThat(registeredUser.getUserId()).isEqualTo("1");
        assertThat(registeredUser.getEmail()).isEqualTo("test@example.com");
        verify(usersRepository).save(any(Users.class));
    }

    @Test
    void testLoginUser_Success() throws ResourceNotFoundException {
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(user);
        
        UserDTO loggedInUser = usersService.loginUser(user.getEmail(), user.getPassword());
       
        assertThat(loggedInUser.getUserId()).isEqualTo(1);
        assertThat(loggedInUser.getEmail()).isEqualTo("test@example.com");
        verify(usersRepository).findByEmail(user.getEmail());
    }

    @Test
    void testLoginUser_Failure() {
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(null);

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usersService.loginUser(user.getEmail(), user.getPassword());
        });

        assertThat(exception.getMessage()).isEqualTo("User does not exist or invalid password");
    }

    @Test
    void testGetUserById() {
        when(usersRepository.findById("1")).thenReturn(Optional.of(user));

        UserDTO foundUser = usersService.getUserById("1");

        assertThat(foundUser.getUserId()).isEqualTo("1");
        verify(usersRepository).findById("1");
    }

    @Test
    void testGetAllUsers() {
        when(usersRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDTO> usersList = usersService.getAllUsers();

        assertThat(usersList).isNotEmpty();
        assertThat(usersList.get(0).getUserId()).isEqualTo("1");
        verify(usersRepository).findAll();
    }

    @Test
    void testUpdateUser() {
        when(usersRepository.findById("1")).thenReturn(Optional.of(user));
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        userDTO.setEmail("updated@example.com");
        UserDTO updatedUser = usersService.updateUser("1", userDTO);

        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
        verify(usersRepository).save(any(Users.class));
    }

    @Test
    void testDeleteUser() {
        usersService.deleteUser("1");
        verify(usersRepository).deleteById("1");
    }

    @Test
    void testGetAllTrainers() {
        when(usersRepository.findAllByRole_RoleName("Trainer")).thenReturn(Arrays.asList(user));

        List<UserDTO> trainers = usersService.getAllTrainers();

        assertThat(trainers).isNotEmpty();
        verify(usersRepository).findAllByRole_RoleName("Trainer");
    }
}
