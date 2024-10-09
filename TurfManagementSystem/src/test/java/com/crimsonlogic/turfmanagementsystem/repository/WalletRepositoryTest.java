package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.entity.Wallet;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use your actual database or configure for in-memory testing
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UsersRepository usersRepository; // Ensure you have this repository

    private Users testUser;
    private Wallet testWallet;

    @BeforeEach
    public void setUp() {
        testUser = new Users();
        testUser.setUserId("1"); // Use a valid ID format
        testUser.setEmail("test@example.com");
        testUser.setPassword("securePassword");
        testUser.setRole(null); // Set a valid role or null based on your design
        
        // Save the user first
        testUser = usersRepository.save(testUser);

        testWallet = new Wallet();
        testWallet.setUser(testUser);
        testWallet.setBalance(BigDecimal.valueOf(100.0)); // Initial balance
    }

    @Test
    public void saveWalletAndFindByUserId() {
        Wallet savedWallet = walletRepository.save(testWallet);
        assertThat(walletRepository.findByUserUserId(testUser.getUserId()).get()).isEqualTo(savedWallet);
    }

    @Test
    public void saveWalletAndFindByUser() {
        walletRepository.save(testWallet);
        Wallet foundWallet = walletRepository.findByUser(testUser).orElse(null);
        assertThat(foundWallet).isNotNull();
        assertThat(foundWallet.getUser().getUserId()).isEqualTo(testUser.getUserId());
    }

    @Test
    public void checkWalletBalance() {
        walletRepository.save(testWallet);
        assertThat(walletRepository.findByUserUserId(testUser.getUserId()).get().getBalance())
            .isEqualByComparingTo(BigDecimal.valueOf(100.0));
    }
}
