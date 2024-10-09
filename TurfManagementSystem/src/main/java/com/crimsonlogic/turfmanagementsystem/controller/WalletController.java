package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * WalletController manages wallet-related HTTP requests, including adding funds, 
 * deducting funds, and retrieving wallet details by user ID.
 * Author: Shradha
 */
import com.crimsonlogic.turfmanagementsystem.dto.AddFundsDTO;
import com.crimsonlogic.turfmanagementsystem.dto.WalletDTO;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Get wallet details by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<WalletDTO> getWalletByUserId(@PathVariable String userId) throws ResourceNotFoundException {
        WalletDTO walletDTO = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(walletDTO);
    }

    
    //adding funds to wallet
    @PutMapping("/add-funds/{userId}")
    public ResponseEntity<WalletDTO> addFunds(@PathVariable String userId, @RequestBody AddFundsDTO addFundsDTO) throws ResourceNotFoundException {
        System.err.println("inside add funds method");
        BigDecimal amount = addFundsDTO.getAmount();
        WalletDTO updatedWallet = walletService.addFunds(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }


    // Update wallet balance directly
    @PutMapping("/update-balance/{userId}")
    public ResponseEntity<WalletDTO> updateWalletBalance(@PathVariable String userId, @RequestBody AddFundsDTO addFundsDTO) throws ResourceNotFoundException {
    	BigDecimal amount = addFundsDTO.getAmount();
    	WalletDTO updatedWallet = walletService.updateWalletBalance(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }
    
    //deduct funds from wallet
    @PutMapping("/deduct-funds/{userId}")
    public ResponseEntity<WalletDTO> deductFunds(@PathVariable String userId,@RequestBody AddFundsDTO addFundsDTO) throws ResourceNotFoundException {
    	BigDecimal amount = addFundsDTO.getAmount();
    	WalletDTO updatedWallet = walletService.deductFunds(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    
}
