package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * TurfController handles turf-related HTTP requests.
 * Author: Shradha
 */


import com.crimsonlogic.turfmanagementsystem.dto.TimeSlotDTO;
import com.crimsonlogic.turfmanagementsystem.dto.TurfDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.service.TimeSlotService;
import com.crimsonlogic.turfmanagementsystem.service.TurfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/turf")  
public class TurfController {

    @Autowired
    private TurfService turfService;
    
    @Autowired
    private TimeSlotService timeSlotService;

    // Create a new Turf
    @PostMapping("/createTurf")
    public ResponseEntity<TurfDTO> createTurf(@RequestBody TurfDTO turfDTO) {
    	TurfDTO createdTurf = turfService.createTurf(turfDTO);
    	

        // Create associated time slots for the newly created turf
        timeSlotService.createSlotsForNewTurf(createdTurf.getTurfId());

        return new ResponseEntity<>(createdTurf, HttpStatus.CREATED);
    }

    // Get a Turf by ID
    @GetMapping("/{turfId}")
    public ResponseEntity<TurfDTO> getTurfById(@PathVariable String turfId) {
    	
    	
        TurfDTO turfDTO = turfService.getTurfById(turfId);
        System.err.println(turfDTO);
        return ResponseEntity.ok(turfDTO); // Return 200 OK
    }
//Get all turfs
    @GetMapping("getturfs")
    public ResponseEntity<List<TurfDTO>> getAllTurfs() {
        List<TurfDTO> turfs = turfService.getAllTurfs();
        return ResponseEntity.ok(turfs); // Return 200 OK
    }
    
    

    // Update an existing Turf
    @PutMapping("/updateturf/{turfId}")
    public ResponseEntity<TurfDTO> updateTurf(@PathVariable String turfId, @RequestBody TurfDTO turfDTO) {
        TurfDTO updatedTurf = turfService.updateTurf(turfId, turfDTO);
        return ResponseEntity.ok(updatedTurf); // Return 200 OK
    }

    // Delete a Turf by ID
    @DeleteMapping("/{turfId}")
    public ResponseEntity<Void> deleteTurf(@PathVariable String turfId) {
        turfService.deleteTurf(turfId);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
    
    //get available slots for that turf
    @GetMapping("/available-slots")
    public ResponseEntity<List<TimeSlotDTO>> getAvailableSlotsForDay(
            @RequestParam("turfId") String turfId,
            @RequestParam("slotDate") String slotDate
    ) {
        LocalDate date = LocalDate.parse(slotDate);
        List<TimeSlotDTO> availableSlots = timeSlotService.findAvailableSlotsForTurfAndDate(turfId, date);

        return ResponseEntity.ok(availableSlots);
    }
    
    //availble turf
    @GetMapping("/available")
    public List<Turf> getAvailableTurfs() {
        return turfService.getAvailableTurfs();
    }
    
   
}
