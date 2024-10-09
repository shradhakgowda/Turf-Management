package com.crimsonlogic.turfmanagementsystem.entity;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsTest {

    private UserDetails userDetails;
    private Users user;

    @BeforeEach
    public void setUp() {
        userDetails = new UserDetails();
        user = Mockito.mock(Users.class); // Mock the Users entity
    }

    @Test
    public void testGenerateId() {
        // Arrange
        String expectedPrefix = "USER";

        // Act
        userDetails.generateId();

        // Assert
        assertThat(userDetails.getUserId()).isNotNull(); // Check that ID is generated
        assertThat(userDetails.getUserId()).startsWith(expectedPrefix); // Check it starts with "USER"
    }

    @Test
    public void testSetAndGetUser() {
        userDetails.setUser(user);
        assertThat(userDetails.getUser()).isEqualTo(user);
    }

    @Test
    public void testSetAndGetFirstName() {
        String firstName = "John";
        userDetails.setFirstName(firstName);
        assertThat(userDetails.getFirstName()).isEqualTo(firstName);
    }

    @Test
    public void testSetAndGetLastName() {
        String lastName = "Doe";
        userDetails.setLastName(lastName);
        assertThat(userDetails.getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testSetAndGetPhoneNumber() {
        String phoneNumber = "1234567890";
        userDetails.setPhoneNumber(phoneNumber);
        assertThat(userDetails.getPhoneNumber()).isEqualTo(phoneNumber);
    }
}
