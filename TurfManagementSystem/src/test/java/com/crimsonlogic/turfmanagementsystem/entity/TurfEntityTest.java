package com.crimsonlogic.turfmanagementsystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class TurfEntityTest {

    private Turf turf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        turf = new Turf();
        turf.setTurfName("Grass Field");
        turf.setTurfInformation("A well-maintained grass field.");
        turf.setTurfPricePerHour(100.0);
        turf.setTurfImage("grass_field.jpg");
        turf.setTurfAvailabality("Available");
    }

    @Test
    public void testGenerateId() {
        // Mock the behavior of CustomPrefixIdentifierGenerator
        String expectedId = "TF-123"; // Assume this is what the generator would return
        mockStatic(CustomPrefixIdentifierGenerator.class);
        when(CustomPrefixIdentifierGenerator.generateId("TF")).thenReturn(expectedId);

        // Call the method to be tested
        turf.generateId();

        // Verify the result
        assertEquals(expectedId, turf.getTurfId());
    }

    @Test
    public void testTurfInitialization() {
        assertEquals("Grass Field", turf.getTurfName());
        assertEquals("A well-maintained grass field.", turf.getTurfInformation());
        assertEquals(100.0, turf.getTurfPricePerHour());
        assertEquals("grass_field.jpg", turf.getTurfImage());
        assertEquals("Available", turf.getTurfAvailabality());
    }

    // Test Getters and Setters
    @Test
    public void testGettersAndSetters() {
        // Test turfName
        turf.setTurfName("New Turf");
        assertEquals("New Turf", turf.getTurfName());

        // Test turfInformation
        turf.setTurfInformation("Updated information about the turf.");
        assertEquals("Updated information about the turf.", turf.getTurfInformation());

        // Test turfPricePerHour
        turf.setTurfPricePerHour(120.0);
        assertEquals(120.0, turf.getTurfPricePerHour());

        // Test turfImage
        turf.setTurfImage("new_turf_image.jpg");
        assertEquals("new_turf_image.jpg", turf.getTurfImage());

        // Test turfAvailabality
        turf.setTurfAvailabality("Not Available");
        assertEquals("Not Available", turf.getTurfAvailabality());
    }
}
