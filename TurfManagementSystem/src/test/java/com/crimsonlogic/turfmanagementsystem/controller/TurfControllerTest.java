package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.TurfDTO;
import com.crimsonlogic.turfmanagementsystem.service.TimeSlotService;
import com.crimsonlogic.turfmanagementsystem.service.TurfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TurfControllerTest {

    @InjectMocks
    private TurfController turfController;

    @Mock
    private TurfService turfService;

    @Mock
    private TimeSlotService timeSlotService;

    private TurfDTO turfDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        turfDTO = new TurfDTO();
        turfDTO.setTurfId("TF-001");
        turfDTO.setTurfName("Grass Turf");
        turfDTO.setTurfPricePerHour(100.0);
        // Set other fields as needed
    }

    @Test
    public void testCreateTurf() {
        when(turfService.createTurf(any(TurfDTO.class))).thenReturn(turfDTO);

        ResponseEntity<TurfDTO> response = turfController.createTurf(turfDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(turfDTO, response.getBody());
        verify(timeSlotService, times(1)).createSlotsForNewTurf("TF-001");
    }

    @Test
    public void testGetTurfById() {
        when(turfService.getTurfById("TF-001")).thenReturn(turfDTO);

        ResponseEntity<TurfDTO> response = turfController.getTurfById("TF-001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(turfDTO, response.getBody());
    }

    @Test
    public void testGetAllTurfs() {
        List<TurfDTO> turfs = new ArrayList<>();
        turfs.add(turfDTO);
        when(turfService.getAllTurfs()).thenReturn(turfs);

        ResponseEntity<List<TurfDTO>> response = turfController.getAllTurfs();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(turfs, response.getBody());
    }

    @Test
    public void testUpdateTurf() {
        when(turfService.updateTurf("TF-001", turfDTO)).thenReturn(turfDTO);

        ResponseEntity<TurfDTO> response = turfController.updateTurf("TF-001", turfDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(turfDTO, response.getBody());
    }

    @Test
    public void testDeleteTurf() {
        ResponseEntity<Void> response = turfController.deleteTurf("TF-001");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(turfService, times(1)).deleteTurf("TF-001");
    }

    
}
