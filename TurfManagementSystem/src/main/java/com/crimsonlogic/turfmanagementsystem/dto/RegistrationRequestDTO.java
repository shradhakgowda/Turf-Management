package com.crimsonlogic.turfmanagementsystem.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationRequestDTO {
    private UserDTO userDTO;
    private UserDetailsDTO userDetailsDTO;
    private String roleName;
}

