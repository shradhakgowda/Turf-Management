package com.crimsonlogic.turfmanagementsystem.repository;

import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
    // Custom query methods can be added here if needed.
	UserDetails findByUser_UserId(String userId);

}
