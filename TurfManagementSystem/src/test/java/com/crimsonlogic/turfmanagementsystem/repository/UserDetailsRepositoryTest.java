package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crimsonlogic.turfmanagementsystem.entity.UserDetails;
import com.crimsonlogic.turfmanagementsystem.entity.Users;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use in-memory database for testing
class UserDetailsRepositoryTest {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UsersRepository usersRepository; // To create and save Users

    private Users testUser;
    private UserDetails testUserDetails;

    @BeforeEach
    public void setUp() {
        // Create and save a Users instance for association
        testUser = new Users();
        testUser.setUserId("user-1");
        testUser.setEmail("test@example.com");
        testUser.setPassword("securePassword");
        testUser = usersRepository.save(testUser); // Save user

        // Create UserDetails associated with the user
        testUserDetails = new UserDetails();
        testUserDetails.setFirstName("John");
        testUserDetails.setLastName("Doe");
        testUserDetails.setPhoneNumber("1234567890");
        testUserDetails.setUser(testUser); // Associate with user

        // Save UserDetails instance
        testUserDetails = userDetailsRepository.save(testUserDetails);
    }

    @Test
    public void saveUserDetailsAndFindById() {
        UserDetails foundUserDetails = userDetailsRepository.findById(testUserDetails.getUserId()).orElse(null);
        assertThat(foundUserDetails).isNotNull();
        assertThat(foundUserDetails).isEqualTo(testUserDetails);
    }

    @Test
    public void findByUser_UserId() {
        UserDetails foundUserDetails = userDetailsRepository.findByUser_UserId(testUser.getUserId());
        assertThat(foundUserDetails).isNotNull();
        assertThat(foundUserDetails.getFirstName()).isEqualTo("John");
        assertThat(foundUserDetails.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void checkUserDetailsExistsByUserId() {
        UserDetails foundUserDetails = userDetailsRepository.findByUser_UserId(testUser.getUserId());
        assertThat(foundUserDetails).isNotNull();
    }

    @Test
    public void deleteUserDetails() {
        userDetailsRepository.delete(testUserDetails);
        UserDetails foundUserDetails = userDetailsRepository.findByUser_UserId(testUser.getUserId());
        assertThat(foundUserDetails).isNull(); // Should be null after deletion
    }
}
