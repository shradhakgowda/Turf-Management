package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.TimeSlotDTO;
import com.crimsonlogic.turfmanagementsystem.entity.TimeSlot;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.TimeSlotRepository;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.TimeSlotServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TimeSlotServiceImplTest {

    @InjectMocks
    private TimeSlotServiceImpl timeSlotService;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private TurfRepository turfRepository;

    private TimeSlotDTO timeSlotDTO;
    private TimeSlot timeSlot;
    private Turf turf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        turf = new Turf();
        turf.setTurfId("TF-001");

        timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setSlotId("TS-001");
        timeSlotDTO.setTurfId("TF-001");
        timeSlotDTO.setStartTime(LocalTime.of(10, 0));
        timeSlotDTO.setEndTime(LocalTime.of(11, 0));
        timeSlotDTO.setSlotAvailability("available");
        timeSlotDTO.setSlotDate(LocalDate.now());

        timeSlot = new TimeSlot();
        timeSlot.setSlotId("TS-001");
        timeSlot.setTurf(turf);
        timeSlot.setStartTime(LocalTime.of(10, 0));
        timeSlot.setEndTime(LocalTime.of(11, 0));
        timeSlot.setSlotAvailability("available");
        timeSlot.setSlotDate(LocalDate.now());
    }

    @Test
    public void testCreateTimeSlot() {
        when(turfRepository.findById("TF-001")).thenReturn(Optional.of(turf));
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(timeSlot);

        TimeSlotDTO createdSlot = timeSlotService.createTimeSlot(timeSlotDTO);

        assertNotNull(createdSlot);
        assertEquals("TS-001", timeSlot.getSlotId()); // Verify that the slotId is set correctly
        assertEquals("TF-001", timeSlot.getTurf().getTurfId()); // Verify turfId
        verify(turfRepository, times(1)).findById("TF-001");
        verify(timeSlotRepository, times(1)).save(any(TimeSlot.class));
    }


    @Test
    public void testGetTimeSlotById() {
        when(timeSlotRepository.findById("TS-001")).thenReturn(Optional.of(timeSlot));

        TimeSlotDTO fetchedSlot = timeSlotService.getTimeSlotById("TS-001");

        assertNotNull(fetchedSlot);
        assertEquals("TS-001", fetchedSlot.getSlotId());
    }

    @Test
    public void testGetTimeSlotById_NotFound() {
        when(timeSlotRepository.findById("TS-002")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            timeSlotService.getTimeSlotById("TS-002");
        });

        assertEquals("Error fetching TimeSlot: TimeSlot not found with id: TS-002", thrown.getMessage());
    }

    @Test
    public void testGetAllTimeSlots() {
        when(timeSlotRepository.findAll()).thenReturn(Collections.singletonList(timeSlot));

        List<TimeSlotDTO> slots = timeSlotService.getAllTimeSlots();

        assertEquals(1, slots.size());
        assertEquals("TS-001", slots.get(0).getSlotId());
    }

    @Test
    public void testUpdateTimeSlot() {
        when(timeSlotRepository.findById("TS-001")).thenReturn(Optional.of(timeSlot));
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(timeSlot);

        TimeSlotDTO updatedSlot = timeSlotService.updateTimeSlot("TS-001", timeSlotDTO);

        assertNotNull(updatedSlot);
        assertEquals("TS-001", updatedSlot.getSlotId());
        verify(timeSlotRepository, times(1)).save(any(TimeSlot.class));
    }

    @Test
    public void testUpdateTimeSlot_NotFound() {
        when(timeSlotRepository.findById("TS-002")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            timeSlotService.updateTimeSlot("TS-002", timeSlotDTO);
        });

        assertEquals("Error updating TimeSlot: TimeSlot not found with id: TS-002", thrown.getMessage());
    }

    @Test
    public void testDeleteTimeSlot() {
        doNothing().when(timeSlotRepository).deleteById("TS-001");

        timeSlotService.deleteTimeSlot("TS-001");

        verify(timeSlotRepository, times(1)).deleteById("TS-001");
    }

  
    @Test
    public void testGetAvailableSlotsByTurfId() {
        when(timeSlotRepository.findByTurf_TurfIdAndSlotDateAfterAndSlotAvailability("TF-001", LocalDate.now(), "available"))
                .thenReturn(Collections.singletonList(timeSlot));

        List<TimeSlotDTO> availableSlots = timeSlotService.getAvailableSlotsByTurfId("TF-001");

        assertEquals(1, availableSlots.size());
        assertEquals("TS-001", availableSlots.get(0).getSlotId());
    }

    @Test
    public void testFindAvailableSlotsForTurfAndDate() {
        when(timeSlotRepository.findByTurf_TurfIdAndSlotDateAndSlotAvailability("TF-001", LocalDate.now(), "Available"))
                .thenReturn(Collections.singletonList(timeSlot));

        List<TimeSlotDTO> availableSlots = timeSlotService.findAvailableSlotsForTurfAndDate("TF-001", LocalDate.now());

        assertEquals(1, availableSlots.size());
        assertEquals("TS-001", availableSlots.get(0).getSlotId());
    }
}
