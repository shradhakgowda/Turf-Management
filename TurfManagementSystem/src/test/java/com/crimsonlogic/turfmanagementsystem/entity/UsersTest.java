package com.crimsonlogic.turfmanagementsystem.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    private Users user;

    @BeforeEach
    public void setUp() {
        user = new Users();
    }

    @Test
    public void testGenerateId() {
        user.generateId();
        assertNotNull(user.getUserId());
        assertTrue(user.getUserId().startsWith("US"));
    }

    @Test
    public void testUserFields() {
        user.setEmail("test@example.com");
        user.setPassword("securePassword");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
    }

    @Test
    public void testRoleAssociation() {
        Roles role = new Roles();
        role.setRoleId(1L); // Set roleId as Long
        user.setRole(role);

        assertNotNull(user.getRole());
        assertEquals(1L, user.getRole().getRoleId());
    }
}
