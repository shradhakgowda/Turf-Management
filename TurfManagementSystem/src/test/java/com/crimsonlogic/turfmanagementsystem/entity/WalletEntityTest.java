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

import java.math.BigDecimal;

public class WalletEntityTest {

    @InjectMocks
    private Wallet wallet;

    @Mock
    private Users user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
    }

    @Test
    public void testGenerateId() {
        // Mock the behavior of CustomPrefixIdentifierGenerator
        String expectedId = "WALL-123"; // Assume this is what the generator would return
        mockStatic(CustomPrefixIdentifierGenerator.class);
        when(CustomPrefixIdentifierGenerator.generateId("WALL")).thenReturn(expectedId);

        // Call the method to be tested
        wallet.generateId();

        // Verify the result
        assertEquals(expectedId, wallet.getUserwalletId());
    }

    @Test
    public void testWalletInitialization() {
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
        assertEquals(user, wallet.getUser());
    }
}
