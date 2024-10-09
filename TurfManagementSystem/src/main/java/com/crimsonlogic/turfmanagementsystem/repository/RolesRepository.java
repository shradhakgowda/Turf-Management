package com.crimsonlogic.turfmanagementsystem.repository;


import com.crimsonlogic.turfmanagementsystem.entity.Roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
	Optional<Roles> findByRoleName(String roleName);
}
