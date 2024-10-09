package com.crimsonlogic.turfmanagementsystem.serviceImpl;

//author:shradha
//time slot service calculation
import com.crimsonlogic.turfmanagementsystem.dto.TimeSlotDTO;

import com.crimsonlogic.turfmanagementsystem.entity.TimeSlot;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.TimeSlotRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;
    
    @Autowired
    private TurfRepository turfRepository;

    //create a time slot
    @Override
    public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        try {
            TimeSlot timeSlot = mapToEntity(timeSlotDTO);
            TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
            return mapToDTO(savedTimeSlot);
        } catch (Exception e) {
            // Handle any exceptions that might occur
            throw new RuntimeException("Error creating TimeSlot: " + e.getMessage());
        }
    }

    //get time slot by id
    @Override
    public TimeSlotDTO getTimeSlotById(String slotId) {
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(slotId)
                    .orElseThrow(() -> new ResourceNotFoundException("TimeSlot not found with id: " + slotId));
            return mapToDTO(timeSlot);
        } catch (Exception e) {
            // Handle any other exceptions
            throw new RuntimeException("Error fetching TimeSlot: " + e.getMessage());
        }
    }

//get all time slots
    @Override
    public List<TimeSlotDTO> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        return timeSlots.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
//update all the time slots
    @Override
    public TimeSlotDTO updateTimeSlot(String slotId, TimeSlotDTO timeSlotDTO) {
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(slotId)
                    .orElseThrow(() -> new ResourceNotFoundException("TimeSlot not found with id: " + slotId));
            
            timeSlot.setStartTime(timeSlotDTO.getStartTime());
            timeSlot.setEndTime(timeSlotDTO.getEndTime());
            timeSlot.setSlotAvailability(timeSlotDTO.getSlotAvailability());
            timeSlot.setSlotDate(timeSlotDTO.getSlotDate());

            TimeSlot updatedTimeSlot = timeSlotRepository.save(timeSlot);
            return mapToDTO(updatedTimeSlot);
        }  catch (Exception e) {
            // Handle any other exceptions
            throw new RuntimeException("Error updating TimeSlot: " + e.getMessage());
        }
    }


    //delete time slot
    @Override
    public void deleteTimeSlot(String slotId) {
        timeSlotRepository.deleteById(slotId);
    }

    // Utility methods to map between entity and DTO
    private TimeSlotDTO mapToDTO(TimeSlot timeSlot) {
        return new TimeSlotDTO(
                timeSlot.getSlotId(),
                timeSlot.getTurf().getTurfId(),  // Assuming turf ID needs to be fetched
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                timeSlot.getSlotAvailability(),
                timeSlot.getSlotDate()
        );
    }

    private TimeSlot mapToEntity(TimeSlotDTO dto) throws ResourceNotFoundException {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setSlotId(dto.getSlotId());
        
        // Fetch the Turf entity using turfId from the DTO
        Turf turf = turfRepository.findById(dto.getTurfId())
                .orElseThrow(() -> new ResourceNotFoundException("Turf not found with id: " + dto.getTurfId()));
        
        timeSlot.setTurf(turf); // Set the fetched Turf entity
        timeSlot.setStartTime(dto.getStartTime());
        timeSlot.setEndTime(dto.getEndTime());
        timeSlot.setSlotAvailability(dto.getSlotAvailability());
        timeSlot.setSlotDate(dto.getSlotDate());
        return timeSlot;    }

    
    //create a time slot for new turf
    public void createSlotsForNewTurf(String turfId) {
        try {
			Turf turf = turfRepository.findById(turfId)
			        .orElseThrow(() -> new ResourceNotFoundException("Turf not found with id: " + turfId));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        LocalDate today = LocalDate.now();
        
        for (int i = 1; i <5; i++) { // Create slots for the next 4 days
        	 LocalDate date = today.plusDays(i);
        	 System.err.println(date);
            for (int hour = 8; hour <20; hour += 4) { // Example slots every 4 hours from 8 AM to 8 PM
                TimeSlotDTO slotDTO = new TimeSlotDTO();
                slotDTO.setTurfId(turfId);
                slotDTO.setSlotDate(date);
                slotDTO.setStartTime(LocalTime.of(hour, 0));
                slotDTO.setEndTime(LocalTime.of(hour + 1, 0)); // End time is 4 hours later
                slotDTO.setSlotAvailability("available"); // Assuming slot is available initially
                createTimeSlot(slotDTO);
            }
        }
    }
    
    
    
 // Run daily at midnight to ensure rolling 5-day slots
    @Scheduled(cron = "0 0 0 * * *") // Every day at midnight
    public void maintainSevenDaySlots() {
        LocalDate today = LocalDate.now();
        System.err.println("Scheduled task running...");
        LocalDate lastDayInRange = today.plusDays(5); 

        List<Turf> allTurfs = turfRepository.findAll();
        for (Turf turf : allTurfs) {
            addSlotsForNextDay(turf.getTurfId(), lastDayInRange); 
        }
    }

    // Helper method to add slots for the next day 
    public void addSlotsForNextDay(String turfId, LocalDate date) {
        for (int hour = 8; hour < 20; hour += 4) { // Example slots every 4 hours from 8 AM to 8 PM
            TimeSlotDTO slotDTO = new TimeSlotDTO();
            slotDTO.setTurfId(turfId);
            slotDTO.setSlotDate(date);
            slotDTO.setStartTime(LocalTime.of(hour, 0));
            slotDTO.setEndTime(LocalTime.of(hour + 1, 0)); // End time is 4 hours later
            slotDTO.setSlotAvailability("available"); // Slot is available initially

            // Create the slot in the system
            createTimeSlot(slotDTO);
        }
    }
    //delete old slots 
@Override
    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void deleteOldTimeSlots() {
        LocalDate today = LocalDate.now();
        timeSlotRepository.deleteByDate(today.minusDays(1));
    }

//list all availble time slots
@Override
public List<TimeSlotDTO> getAvailableSlotsByTurfId(String turfId) {
    LocalDate today = LocalDate.now();
    List<TimeSlot> availableSlots = timeSlotRepository.findByTurf_TurfIdAndSlotDateAfterAndSlotAvailability(turfId, today, "available");
    		
    return availableSlots.stream().map(this::mapToDTO).collect(Collectors.toList());
}

//find available slots and slots after after current date
@Override
public List<TimeSlotDTO> findAvailableSlotsForTurfAndDate(String turfId, LocalDate slotDate) {
    // Fetch available slots for the turf on the given date where availability is 'Available'
    List<TimeSlot> availableSlots = timeSlotRepository
            .findByTurf_TurfIdAndSlotDateAndSlotAvailability(turfId, slotDate, "Available");

    // Map the entity to DTO and return the list
    return availableSlots.stream()
            .map(slot -> new TimeSlotDTO(
                    slot.getSlotId(),
                    slot.getTurf().getTurfId(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.getSlotAvailability(),
                    slot.getSlotDate()
            ))
            .collect(Collectors.toList());
}


}
