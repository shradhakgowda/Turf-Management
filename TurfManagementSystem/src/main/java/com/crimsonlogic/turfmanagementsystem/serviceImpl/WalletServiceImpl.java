package com.crimsonlogic.turfmanagementsystem.serviceImpl;
//author:shradha
//wallet service calculations
import com.crimsonlogic.turfmanagementsystem.dto.WalletDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Wallet;
import com.crimsonlogic.turfmanagementsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.turfmanagementsystem.repository.WalletRepository;
import com.crimsonlogic.turfmanagementsystem.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    //get walltet by user id
    @Override
    public WalletDTO getWalletByUserId(String userId) throws ResourceNotFoundException {
        Wallet wallet = walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user ID: " + userId));
        return mapToDTO(wallet);
    }

    //update wallet balance
    @Override
    public WalletDTO updateWalletBalance(String userId, BigDecimal amount) throws ResourceNotFoundException {
        Wallet wallet = walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user ID: " + userId));
        wallet.setBalance(amount);
        walletRepository.save(wallet);
        return mapToDTO(wallet);
    }

    //add funds to wallet
    @Override
    public WalletDTO addFunds(String userId, BigDecimal amount) throws ResourceNotFoundException {
        Wallet wallet = walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user ID: " + userId));
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);
        return new WalletDTO(wallet.getUserwalletId(), wallet.getUser().getUserId(), wallet.getBalance());
    }
    
    //deduct funds in wallet
    @Override
    public WalletDTO deductFunds(String userId, BigDecimal amount) throws ResourceNotFoundException {
        Wallet wallet = walletRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user ID: " + userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        return new WalletDTO(wallet.getUserwalletId(), wallet.getUser().getUserId(), wallet.getBalance());
    }


    //map to wallet dto
    public WalletDTO mapToDTO(Wallet wallet) {
        return new WalletDTO(
                wallet.getUserwalletId(),
                wallet.getUser().getUserId(),
                wallet.getBalance()
        );
    }
}
