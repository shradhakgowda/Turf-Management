package com.crimsonlogic.turfmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
