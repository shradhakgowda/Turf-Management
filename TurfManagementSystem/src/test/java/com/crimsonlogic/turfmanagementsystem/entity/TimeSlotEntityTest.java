package com.crimsonlogic.turfmanagementsystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotEntityTest {

    @InjectMocks
    private TimeSlot timeSlot;

    @Mock
    private Turf turf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        timeSlot = new TimeSlot();
        timeSlot.setTurf(turf);
        timeSlot.setStartTime(LocalTime.of(9, 0));  // 9:00 AM
        timeSlot.setEndTime(LocalTime.of(10, 0));    // 10:00 AM
        timeSlot.setSlotAvailability("Available");
        timeSlot.setSlotDate(LocalDate.now());
    }

    @Test
    public void testGenerateId() {
        // Mock the behavior of CustomPrefixIdentifierGenerator
        String expectedId = "SL-123"; // Assume this is what the generator would return
        mockStatic(CustomPrefixIdentifierGenerator.class);
        when(CustomPrefixIdentifierGenerator.generateId("SL")).thenReturn(expectedId);

        // Call the method to be tested
        timeSlot.generateId();

        // Verify the result
        assertEquals(expectedId, timeSlot.getSlotId());
    }

    @Test
    public void testTimeSlotInitialization() {
        assertEquals(turf, timeSlot.getTurf());
        assertEquals(LocalTime.of(9, 0), timeSlot.getStartTime());
        assertEquals(LocalTime.of(10, 0), timeSlot.getEndTime());
        assertEquals("Available", timeSlot.getSlotAvailability());
        assertEquals(LocalDate.now(), timeSlot.getSlotDate());
    }

    // Test Getters and Setters
    @Test
    public void testGettersAndSetters() {
        // Test turf
        Turf newTurf = mock(Turf.class);
        timeSlot.setTurf(newTurf);
        assertEquals(newTurf, timeSlot.getTurf());

        // Test startTime
        LocalTime newStartTime = LocalTime.of(8, 0); // 8:00 AM
        timeSlot.setStartTime(newStartTime);
        assertEquals(newStartTime, timeSlot.getStartTime());

        // Test endTime
        LocalTime newEndTime = LocalTime.of(11, 0); // 11:00 AM
        timeSlot.setEndTime(newEndTime);
        assertEquals(newEndTime, timeSlot.getEndTime());

        // Test slotAvailability
        String newAvailability = "Not Available";
        timeSlot.setSlotAvailability(newAvailability);
        assertEquals(newAvailability, timeSlot.getSlotAvailability());

        // Test slotDate
        LocalDate newSlotDate = LocalDate.now().plusDays(1);
        timeSlot.setSlotDate(newSlotDate);
        assertEquals(newSlotDate, timeSlot.getSlotDate());
    }
}
