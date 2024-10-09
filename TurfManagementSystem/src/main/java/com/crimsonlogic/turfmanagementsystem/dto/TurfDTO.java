package com.crimsonlogic.turfmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurfDTO {
	private String turfId; // ID with custom prefix
	private String turfName; // Name of the turf
	private String turfInformation; // Additional information about the turf
	private Double turfPricePerHour; // Price per hour
	private String turfImage;
	private String turfAvailabality;// Image URL or base64 encoded image
}
