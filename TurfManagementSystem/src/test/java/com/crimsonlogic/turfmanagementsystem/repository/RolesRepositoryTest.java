package com.crimsonlogic.turfmanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use your actual database or configure for in-memory testing
class RolesRepositoryTest {

    @Autowired
    private RolesRepository rolesRepository;

    private Roles testRole;

    @BeforeEach
    public void setUp() {
        testRole = new Roles();
        testRole.setRoleName("Admin1");
        rolesRepository.save(testRole);
    }

    @Test
    public void testFindByRoleName_ShouldReturnRole_WhenRoleExists() {
        Optional<Roles> foundRole = rolesRepository.findByRoleName("Admin1");
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo("Admin1");
    }

    @Test
    public void testFindByRoleName_ShouldReturnEmpty_WhenRoleDoesNotExist() {
        Optional<Roles> foundRole = rolesRepository.findByRoleName("User");
        assertThat(foundRole).isNotPresent();
    }

    @Test
    public void testSaveRole_ShouldPersistRole() {
        Roles newRole = new Roles();
        newRole.setRoleName("User");
        Roles savedRole = rolesRepository.save(newRole);
        
        assertThat(savedRole.getRoleId()).isNotNull();
        assertThat(savedRole.getRoleName()).isEqualTo("User");
    }

    @Test
    public void testFindById_ShouldReturnRole_WhenRoleExists() {
        Optional<Roles> foundRole = rolesRepository.findById(testRole.getRoleId());
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get()).isEqualTo(testRole);
    }

    @Test
    public void testDeleteRole_ShouldRemoveRole() {
        rolesRepository.delete(testRole);
        Optional<Roles> foundRole = rolesRepository.findById(testRole.getRoleId());
        assertThat(foundRole).isNotPresent();
    }
}
