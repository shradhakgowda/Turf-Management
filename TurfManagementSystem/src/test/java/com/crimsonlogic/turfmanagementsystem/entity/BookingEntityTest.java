package com.crimsonlogic.turfmanagementsystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;

import com.crimsonlogic.turfmanagementsystem.utils.CustomPrefixIdentifierGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class BookingEntityTest {

    @InjectMocks
    private Booking booking;

    @Mock
    private Users customer;

    @Mock
    private Users assignedTrainer;

    @Mock
    private Turf turf;

    @Mock
    private TimeSlot slot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = new Booking();
        booking.setCustomer(customer);
        booking.setAssignedTrainer(assignedTrainer);
        booking.setTurf(turf);
        booking.setSlot(slot);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalAmount(100.0);
        booking.setStatus("Confirmed");
    }

    @Test
    public void testGenerateId() {
        // Mock the behavior of CustomPrefixIdentifierGenerator
        String expectedId = "BK-123"; // Assume this is what the generator would return
        mockStatic(CustomPrefixIdentifierGenerator.class);
        when(CustomPrefixIdentifierGenerator.generateId("BK")).thenReturn(expectedId);

        // Call the method to be tested
        booking.generateId();

        // Verify the result
        assertEquals(expectedId, booking.getBookingId());
    }

    @Test
    public void testBookingInitialization() {
        assertEquals(customer, booking.getCustomer());
        assertEquals(assignedTrainer, booking.getAssignedTrainer());
        assertEquals(turf, booking.getTurf());
        assertEquals(slot, booking.getSlot());
        assertEquals(LocalDate.now(), booking.getBookingDate());
        assertEquals(100.0, booking.getTotalAmount());
        assertEquals("Confirmed", booking.getStatus());
    }
}
