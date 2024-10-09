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

public class PaymentEntityTest {

    @InjectMocks
    private Payment payment;

    @Mock
    private Users user;

    @Mock
    private Booking booking;

    @Mock
    private Turf turf;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setUser(user);
        payment.setBooking(booking);
        payment.setTurf(turf);
        payment.setAmount(150.0);
        payment.setTransactionType("Credit");
    }

    @Test
    public void testGenerateId() {
        // Mock the behavior of CustomPrefixIdentifierGenerator
        String expectedId = "PM-123"; // Assume this is what the generator would return
        mockStatic(CustomPrefixIdentifierGenerator.class);
        when(CustomPrefixIdentifierGenerator.generateId("PM")).thenReturn(expectedId);

        // Call the method to be tested
        payment.generateId();

        // Verify the result
        assertEquals(expectedId, payment.getPaymentId());
    }

    @Test
    public void testPaymentInitialization() {
        assertEquals(user, payment.getUser());
        assertEquals(booking, payment.getBooking());
        assertEquals(turf, payment.getTurf());
        assertEquals(150.0, payment.getAmount());
        assertEquals("Credit", payment.getTransactionType());
    }

    // Test Getters and Setters
    @Test
    public void testGettersAndSetters() {
        // Test user
        Users newUser = mock(Users.class);
        payment.setUser(newUser);
        assertEquals(newUser, payment.getUser());

        // Test booking
        Booking newBooking = mock(Booking.class);
        payment.setBooking(newBooking);
        assertEquals(newBooking, payment.getBooking());

        // Test turf
        Turf newTurf = mock(Turf.class);
        payment.setTurf(newTurf);
        assertEquals(newTurf, payment.getTurf());

        // Test amount
        double newAmount = 200.0;
        payment.setAmount(newAmount);
        assertEquals(newAmount, payment.getAmount());

        // Test transactionType
        String newTransactionType = "Debit";
        payment.setTransactionType(newTransactionType);
        assertEquals(newTransactionType, payment.getTransactionType());
    }
}
