package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.entity.Users;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use your actual database or configure for in-memory testing
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private Users testUser;
    private Users anotherUser;

    @BeforeEach
    public void setUp() {
        Roles role = new Roles();
        role.setRoleId(1L); // Ensure this role ID exists in your database for testing

        testUser = new Users();
        testUser.setUserId("1"); // Use a valid ID format
        testUser.setEmail("test@example.com");
        testUser.setPassword("securePassword");
        testUser.setRole(role);
        
        anotherUser = new Users();
        anotherUser.setUserId("2"); // Use a valid ID format
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setRole(role);
    }

    @Test
    public void saveUserAndFindById() {
        Users savedUser = usersRepository.save(testUser);
        assertThat(usersRepository.findById(savedUser.getUserId()).get()).isEqualTo(savedUser);
    }

    @Test
    public void saveUserAndFindByEmail() {
        usersRepository.save(anotherUser);
        Users foundUser = usersRepository.findByEmail("another@example.com");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("another@example.com");
    }

    @Test
    public void checkUserExistsByEmail() {
        usersRepository.save(testUser);
        assertThat(usersRepository.existsByEmail("test@example.com")).isTrue();
        assertThat(usersRepository.existsByEmail("nonexistent@example.com")).isFalse();
    }
}
