package com.crimsonlogic.turfmanagementsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.crimsonlogic.turfmanagementsystem.dto.WalletDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.entity.Wallet;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.WalletRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.WalletServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    private Wallet testWallet;
    private Users testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testUser = new Users();
        testUser.setUserId("1");
        
        testWallet = new Wallet();
        testWallet.setUserwalletId("WALL001");
        testWallet.setUser(testUser);
        testWallet.setBalance(BigDecimal.valueOf(100.0));
    }

    @Test
    public void getWalletByUserId_ShouldReturnWalletDTO_WhenWalletExists() throws ResourceNotFoundException {
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.of(testWallet));

        WalletDTO walletDTO = walletService.getWalletByUserId(testUser.getUserId());

        assertThat(walletDTO).isNotNull();
        assertThat(walletDTO.getUserwalletId()).isEqualTo(testWallet.getUserwalletId());
        assertThat(walletDTO.getUserId()).isEqualTo(testUser.getUserId());
        assertThat(walletDTO.getBalance()).isEqualTo(testWallet.getBalance());
    }

    @Test
    public void getWalletByUserId_ShouldThrowException_WhenWalletDoesNotExist() {
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.getWalletByUserId(testUser.getUserId()))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Wallet not found for user ID: " + testUser.getUserId());
    }

    @Test
    public void updateWalletBalance_ShouldReturnUpdatedWalletDTO_WhenWalletExists() throws ResourceNotFoundException {
        BigDecimal newBalance = BigDecimal.valueOf(150.0);
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.of(testWallet));

        WalletDTO updatedWalletDTO = walletService.updateWalletBalance(testUser.getUserId(), newBalance);

        assertThat(updatedWalletDTO.getBalance()).isEqualTo(newBalance);
        verify(walletRepository).save(testWallet);
    }

    @Test
    public void updateWalletBalance_ShouldThrowException_WhenWalletDoesNotExist() {
        BigDecimal newBalance = BigDecimal.valueOf(150.0);
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.updateWalletBalance(testUser.getUserId(), newBalance))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Wallet not found for user ID: " + testUser.getUserId());
    }

    @Test
    public void addFunds_ShouldIncreaseWalletBalance_WhenWalletExists() throws ResourceNotFoundException {
        BigDecimal amountToAdd = BigDecimal.valueOf(50.0);
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.of(testWallet));

        WalletDTO updatedWalletDTO = walletService.addFunds(testUser.getUserId(), amountToAdd);

        assertThat(updatedWalletDTO.getBalance()).isEqualTo(BigDecimal.valueOf(150.0));
        verify(walletRepository).save(testWallet);
    }

    @Test
    public void addFunds_ShouldThrowException_WhenWalletDoesNotExist() {
        BigDecimal amountToAdd = BigDecimal.valueOf(50.0);
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.addFunds(testUser.getUserId(), amountToAdd))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Wallet not found for user ID: " + testUser.getUserId());
    }

    @Test
    public void deductFunds_ShouldDecreaseWalletBalance_WhenWalletExistsAndSufficientFunds() throws ResourceNotFoundException {
        BigDecimal amountToDeduct = BigDecimal.valueOf(30.0);
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.of(testWallet));

        WalletDTO updatedWalletDTO = walletService.deductFunds(testUser.getUserId(), amountToDeduct);

        assertThat(updatedWalletDTO.getBalance()).isEqualTo(BigDecimal.valueOf(70.0));
        verify(walletRepository).save(testWallet);
    }

    @Test
    public void deductFunds_ShouldThrowException_WhenWalletDoesNotExist() {
        BigDecimal amountToDeduct = BigDecimal.valueOf(30.0);
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.deductFunds(testUser.getUserId(), amountToDeduct))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Wallet not found for user ID: " + testUser.getUserId());
    }

    @Test
    public void deductFunds_ShouldThrowException_WhenInsufficientFunds() {
        BigDecimal amountToDeduct = BigDecimal.valueOf(200.0); // More than current balance
        when(walletRepository.findByUserUserId(testUser.getUserId())).thenReturn(Optional.of(testWallet));

        assertThatThrownBy(() -> walletService.deductFunds(testUser.getUserId(), amountToDeduct))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Insufficient funds");
    }
}
