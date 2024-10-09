package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.TimeSlotDTO;
import com.crimsonlogic.turfmanagementsystem.service.TimeSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TimeSlotControllerTest {

    @InjectMocks
    private TimeSlotController timeSlotController;

    @Mock
    private TimeSlotService timeSlotService;

    private TimeSlotDTO timeSlotDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setSlotId("TS-001");
        timeSlotDTO.setTurfId("TF-001");
        // Set other properties as needed
    }

    @Test
    public void testCreateTimeSlot() {
        when(timeSlotService.createTimeSlot(any(TimeSlotDTO.class))).thenReturn(timeSlotDTO);

        ResponseEntity<TimeSlotDTO> response = timeSlotController.createTimeSlot(timeSlotDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlotDTO, response.getBody());
        verify(timeSlotService, times(1)).createTimeSlot(any(TimeSlotDTO.class));
    }

    @Test
    public void testGetTimeSlotById() {
        when(timeSlotService.getTimeSlotById("TS-001")).thenReturn(timeSlotDTO);

        ResponseEntity<TimeSlotDTO> response = timeSlotController.getTimeSlotById("TS-001");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlotDTO, response.getBody());
        verify(timeSlotService, times(1)).getTimeSlotById("TS-001");
    }

    @Test
    public void testUpdateTimeSlot() {
        when(timeSlotService.updateTimeSlot(eq("TS-001"), any(TimeSlotDTO.class))).thenReturn(timeSlotDTO);

        ResponseEntity<TimeSlotDTO> response = timeSlotController.updateTimeSlot("TS-001", timeSlotDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlotDTO, response.getBody());
        verify(timeSlotService, times(1)).updateTimeSlot(eq("TS-001"), any(TimeSlotDTO.class));
    }

    @Test
    public void testDeleteTimeSlot() {
        doNothing().when(timeSlotService).deleteTimeSlot("TS-001");

        ResponseEntity<Void> response = timeSlotController.deleteTimeSlot("TS-001");

        assertEquals(204, response.getStatusCodeValue());
        verify(timeSlotService, times(1)).deleteTimeSlot("TS-001");
    }

    @Test
    public void testGetAllTimeSlots() {
        List<TimeSlotDTO> timeSlots = new ArrayList<>();
        timeSlots.add(timeSlotDTO);
        when(timeSlotService.getAllTimeSlots()).thenReturn(timeSlots);

        ResponseEntity<List<TimeSlotDTO>> response = timeSlotController.getAllTimeSlots();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlots, response.getBody());
        verify(timeSlotService, times(1)).getAllTimeSlots();
    }

    @Test
    public void testGetAvailableSlotsByTurf() {
        List<TimeSlotDTO> availableSlots = new ArrayList<>();
        availableSlots.add(timeSlotDTO);
        when(timeSlotService.getAvailableSlotsByTurfId("TF-001")).thenReturn(availableSlots);

        ResponseEntity<List<TimeSlotDTO>> response = timeSlotController.getAvailableSlotsByTurf("TF-001");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(availableSlots, response.getBody());
        verify(timeSlotService, times(1)).getAvailableSlotsByTurfId("TF-001");
    }
}
