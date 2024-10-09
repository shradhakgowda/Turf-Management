package com.crimsonlogic.turfmanagementsystem.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.crimsonlogic.turfmanagementsystem.dto.RolesDTO;
import com.crimsonlogic.turfmanagementsystem.service.RolesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class RolesControllerTest {

    @InjectMocks
    private RolesController rolesController;

    @Mock
    private RolesService rolesService;

    private RolesDTO testRoleDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testRoleDTO = new RolesDTO();
        testRoleDTO.setRoleId(1L);
        testRoleDTO.setRoleName("Admin");
        testRoleDTO.setUserIds(Collections.emptyList());
    }

    @Test
    public void createRole_ShouldReturnCreatedRole() {
        when(rolesService.createRole(any(RolesDTO.class))).thenReturn(testRoleDTO);

        ResponseEntity<RolesDTO> response = rolesController.createRole(testRoleDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(testRoleDTO);
    }

    @Test
    public void getRoleById_ShouldReturnRole_WhenExists() {
        when(rolesService.getRoleById(1L)).thenReturn(testRoleDTO);

        ResponseEntity<RolesDTO> response = rolesController.getRoleById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(testRoleDTO);
    }

    @Test
    public void getRoleById_ShouldReturnNotFound_WhenRoleDoesNotExist() {
        when(rolesService.getRoleById(1L)).thenReturn(null);

        ResponseEntity<RolesDTO> response = rolesController.getRoleById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAllRoles_ShouldReturnListOfRoles() {
        when(rolesService.getAllRoles()).thenReturn(Arrays.asList(testRoleDTO));

        ResponseEntity<List<RolesDTO>> response = rolesController.getAllRoles();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(testRoleDTO);
    }

    @Test
    public void deleteRole_ShouldReturnNoContent() {
        doNothing().when(rolesService).deleteRole(1L);

        ResponseEntity<Void> response = rolesController.deleteRole(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
