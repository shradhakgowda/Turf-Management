package com.crimsonlogic.turfmanagementsystem;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TurfManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurfManagementSystemApplication.class, args);
	}

}
