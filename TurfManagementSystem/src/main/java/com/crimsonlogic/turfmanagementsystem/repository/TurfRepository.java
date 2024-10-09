package com.crimsonlogic.turfmanagementsystem.repository;


import com.crimsonlogic.turfmanagementsystem.entity.Turf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurfRepository extends JpaRepository<Turf, String> {
    @Query("SELECT t FROM Turf t WHERE t.turfAvailabality = 'available'")
    List<Turf> findAvailableTurfs();
}

