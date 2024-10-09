package com.crimsonlogic.turfmanagementsystem.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserDTO user; 
}
