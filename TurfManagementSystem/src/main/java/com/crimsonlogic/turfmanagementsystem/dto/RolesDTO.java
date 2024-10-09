package com.crimsonlogic.turfmanagementsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesDTO {
  
	private Long roleId;
    private String roleName;
    private List<String> userIds;  // Only store user IDs for the DTO
}
