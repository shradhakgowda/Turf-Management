package com.crimsonlogic.turfmanagementsystem.repository;


import com.crimsonlogic.turfmanagementsystem.entity.Users;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    // Custom query methods (if needed) can be defined here.
	 boolean existsByEmail(String email); 
	 Users findByEmail(String email);
	 List<Users> findAllByRole_RoleName(String roleName);
	 List<Users> findByRole_RoleName(String roleName);
	 
}

