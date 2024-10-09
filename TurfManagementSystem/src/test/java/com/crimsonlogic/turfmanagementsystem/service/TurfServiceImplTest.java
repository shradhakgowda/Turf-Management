package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.TurfDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Turf;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.TurfRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.TurfServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TurfServiceImplTest {

    @InjectMocks
    private TurfServiceImpl turfService;

    @Mock
    private TurfRepository turfRepository;

    private TurfDTO turfDTO;
    private Turf turf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize TurfDTO and Turf for tests
        turfDTO = new TurfDTO();
        turfDTO.setTurfId("TF-001");
        turfDTO.setTurfName("Soccer Field");
        turfDTO.setTurfInformation("A well-maintained soccer field.");
        turfDTO.setTurfPricePerHour(100.0);
        turfDTO.setTurfImage("soccer_field.jpg");
        turfDTO.setTurfAvailabality("Available");

        turf = new Turf();
        turf.setTurfId(turfDTO.getTurfId());
        turf.setTurfName(turfDTO.getTurfName());
        turf.setTurfInformation(turfDTO.getTurfInformation());
        turf.setTurfPricePerHour(turfDTO.getTurfPricePerHour());
        turf.setTurfImage(turfDTO.getTurfImage());
        turf.setTurfAvailabality(turfDTO.getTurfAvailabality());
    }

    @Test
    public void testCreateTurf() {
        when(turfRepository.save(any(Turf.class))).thenReturn(turf);

        TurfDTO createdTurf = turfService.createTurf(turfDTO);

        assertNotNull(createdTurf);
        assertEquals(turfDTO.getTurfId(), createdTurf.getTurfId());
        verify(turfRepository, times(1)).save(any(Turf.class));
    }

    @Test
    public void testGetTurfById() {
        when(turfRepository.findById("TF-001")).thenReturn(Optional.of(turf));

        TurfDTO foundTurf = turfService.getTurfById("TF-001");

        assertNotNull(foundTurf);
        assertEquals(turfDTO.getTurfId(), foundTurf.getTurfId());
        verify(turfRepository, times(1)).findById("TF-001");
    }

    @Test
    public void testGetTurfById_NotFound() {
        when(turfRepository.findById("TF-001")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            turfService.getTurfById("TF-001");
        });

        // Optionally, you can assert the message if you expect a specific message
        assertEquals("An error occurred while retrieving the turf.", thrown.getMessage());
    }

    @Test
    public void testGetAllTurfs() {
        when(turfRepository.findAll()).thenReturn(Arrays.asList(turf));

        List<TurfDTO> turfs = turfService.getAllTurfs();

        assertNotNull(turfs);
        assertEquals(1, turfs.size());
        assertEquals(turfDTO.getTurfId(), turfs.get(0).getTurfId());
        verify(turfRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateTurf() {
        when(turfRepository.findById("TF-001")).thenReturn(Optional.of(turf));
        when(turfRepository.save(any(Turf.class))).thenReturn(turf);

        TurfDTO updatedTurfDTO = new TurfDTO();
        updatedTurfDTO.setTurfName("Updated Soccer Field");

        TurfDTO updatedTurf = turfService.updateTurf("TF-001", updatedTurfDTO);

        assertNotNull(updatedTurf);
        assertEquals("Updated Soccer Field", updatedTurf.getTurfName());
        verify(turfRepository, times(1)).findById("TF-001");
        verify(turfRepository, times(1)).save(any(Turf.class));
    }

    @Test
    public void testUpdateTurf_NotFound() {
        when(turfRepository.findById("TF-001")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            turfService.updateTurf("TF-001", turfDTO);
        });

        assertEquals("Turf not found with id: TF-001", thrown.getMessage());
    }

    @Test
    public void testDeleteTurf() {
        doNothing().when(turfRepository).deleteById("TF-001");

        turfService.deleteTurf("TF-001");

        verify(turfRepository, times(1)).deleteById("TF-001");
    }

    @Test
    public void testGetAvailableTurfs() {
        when(turfRepository.findAvailableTurfs()).thenReturn(Arrays.asList(turf));

        List<Turf> availableTurfs = turfService.getAvailableTurfs();

        assertNotNull(availableTurfs);
        assertEquals(1, availableTurfs.size());
        verify(turfRepository, times(1)).findAvailableTurfs();
    }
}
