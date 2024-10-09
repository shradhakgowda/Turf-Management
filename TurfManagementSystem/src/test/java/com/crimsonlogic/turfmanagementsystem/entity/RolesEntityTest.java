package com.crimsonlogic.turfmanagementsystem.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RolesEntityTest {

    private Roles roles;

    @BeforeEach
    public void setUp() {
        roles = new Roles();
        roles.setRoleName("Admin");
        roles.setUsers(new ArrayList<>());
    }

    @Test
    public void testRoleName() {
        assertThat(roles.getRoleName()).isEqualTo("Admin");
        roles.setRoleName("User");
        assertThat(roles.getRoleName()).isEqualTo("User");
    }

    @Test
    public void testRoleId() {
        // ID should be null before saving
        assertThat(roles.getRoleId()).isNull();
        // If we had a saved instance, we could assert the ID is not null
        roles.setRoleId(1L);
        assertThat(roles.getRoleId()).isEqualTo(1L);
    }

    @Test
    public void testUsersList() {
        List<Users> usersList = new ArrayList<>();
        Users user1 = new Users();
        user1.setEmail("user1@example.com");
        user1.setRole(roles);
        
        Users user2 = new Users();
        user2.setEmail("user2@example.com");
        user2.setRole(roles);
        
        usersList.add(user1);
        usersList.add(user2);
        
        roles.setUsers(usersList);
        
        assertThat(roles.getUsers()).hasSize(2);
        assertThat(roles.getUsers()).containsExactly(user1, user2);
    }

    @Test
    public void testAddUser() {
        Users user = new Users();
        user.setEmail("user@example.com");
        
        roles.getUsers().add(user);
        
        assertThat(roles.getUsers()).contains(user);
    }

    @Test
    public void testRemoveUser() {
        Users user = new Users();
        user.setEmail("user@example.com");
        
        roles.getUsers().add(user);
        roles.getUsers().remove(user);
        
        assertThat(roles.getUsers()).doesNotContain(user);
    }
}
