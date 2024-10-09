package com.crimsonlogic.turfmanagementsystem.controller;

import com.crimsonlogic.turfmanagementsystem.dto.AddFundsDTO;
import com.crimsonlogic.turfmanagementsystem.dto.WalletDTO;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private WalletService walletService;

    private WalletDTO mockWallet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a mock wallet for tests
        mockWallet = new WalletDTO("1", "userId", BigDecimal.valueOf(100.00));
    }

    @Test
    void getWalletByUserId_ShouldReturnWallet_WhenWalletExists() throws ResourceNotFoundException {
        when(walletService.getWalletByUserId("userId")).thenReturn(mockWallet);

        ResponseEntity<WalletDTO> response = walletController.getWalletByUserId("userId");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockWallet, response.getBody());
    }

    @Test
    void addFunds_ShouldUpdateWallet_WhenFundsAreAdded() throws ResourceNotFoundException {
        AddFundsDTO addFundsDTO = new AddFundsDTO(BigDecimal.valueOf(50.00));
        when(walletService.addFunds("userId", BigDecimal.valueOf(50.00))).thenReturn(mockWallet);

        ResponseEntity<WalletDTO> response = walletController.addFunds("userId", addFundsDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockWallet, response.getBody());
    }

    @Test
    void deductFunds_ShouldUpdateWallet_WhenFundsAreDeducted() throws ResourceNotFoundException {
        AddFundsDTO deductFundsDTO = new AddFundsDTO(BigDecimal.valueOf(30.00));
        when(walletService.deductFunds("userId", BigDecimal.valueOf(30.00))).thenReturn(mockWallet);

        ResponseEntity<WalletDTO> response = walletController.deductFunds("userId", deductFundsDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockWallet, response.getBody());
    }

    // Additional tests for exception handling can be added here, if necessary.
}
