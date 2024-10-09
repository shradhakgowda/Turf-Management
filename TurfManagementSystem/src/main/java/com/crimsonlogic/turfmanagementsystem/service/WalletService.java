package com.crimsonlogic.turfmanagementsystem.service;

import java.math.BigDecimal;

import com.crimsonlogic.turfmanagementsystem.dto.WalletDTO;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;

public interface WalletService {
    WalletDTO getWalletByUserId(String userId) throws ResourceNotFoundException;
    WalletDTO updateWalletBalance(String userId, BigDecimal amount) throws ResourceNotFoundException;
    public WalletDTO addFunds(String userId, BigDecimal amount) throws ResourceNotFoundException;
    public WalletDTO deductFunds(String userId, BigDecimal amount) throws ResourceNotFoundException;
}
