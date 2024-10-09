package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * TimeSlotController handles time slot-related HTTP requests.
 * Author: Shradha
 */

import com.crimsonlogic.turfmanagementsystem.dto.TimeSlotDTO;

import com.crimsonlogic.turfmanagementsystem.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    
    //create time slot
    @PostMapping
    public ResponseEntity<TimeSlotDTO> createTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        TimeSlotDTO createdTimeSlot = timeSlotService.createTimeSlot(timeSlotDTO);
        return ResponseEntity.ok(createdTimeSlot);
    }

    
    //get slot by id
    @GetMapping("/getslot/{slotId}")
    public ResponseEntity<TimeSlotDTO> getTimeSlotById(@PathVariable String slotId) {
        TimeSlotDTO timeSlotDTO = timeSlotService.getTimeSlotById(slotId);
        return ResponseEntity.ok(timeSlotDTO);
    }

    
    //update slot by id
    @PutMapping("/{slotId}")
    public ResponseEntity<TimeSlotDTO> updateTimeSlot(
            @PathVariable String slotId,
            @RequestBody TimeSlotDTO timeSlotDTO) {
        TimeSlotDTO updatedTimeSlot = timeSlotService.updateTimeSlot(slotId, timeSlotDTO);
        return ResponseEntity.ok(updatedTimeSlot);
    }

    
    //delete slot
    @DeleteMapping("/{slotId}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable String slotId) {
        timeSlotService.deleteTimeSlot(slotId);
        return ResponseEntity.noContent().build();
    }

    //list all the slots
    @GetMapping
    public ResponseEntity<List<TimeSlotDTO>> getAllTimeSlots() {
        List<TimeSlotDTO> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }
    
    
    //get all the slots that are available
    @GetMapping("/available/{turfId}")
    public ResponseEntity<List<TimeSlotDTO>> getAvailableSlotsByTurf(@PathVariable String turfId) {
         
        List<TimeSlotDTO> availableSlots = timeSlotService.getAvailableSlotsByTurfId(turfId);
        return ResponseEntity.ok(availableSlots);
    }
    
}
