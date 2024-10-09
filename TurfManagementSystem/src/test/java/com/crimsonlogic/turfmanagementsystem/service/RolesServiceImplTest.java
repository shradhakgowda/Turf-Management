package com.crimsonlogic.turfmanagementsystem.service;



import static org.assertj.core.api.Assertions.assertThat;


import static org.mockito.Mockito.*;

import com.crimsonlogic.turfmanagementsystem.dto.RolesDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.repository.RolesRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository;
import com.crimsonlogic.turfmanagementsystem.serviceImpl.RolesServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class RolesServiceImplTest {

    @InjectMocks
    private RolesServiceImpl rolesService;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private UsersRepository usersRepository;

    private Roles testRole;
    private RolesDTO testRoleDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testRole = new Roles();
        testRole.setRoleId(1L);
        testRole.setRoleName("Admin");
        testRole.setUsers(Collections.emptyList());

        testRoleDTO = new RolesDTO();
        testRoleDTO.setRoleId(1L);
        testRoleDTO.setRoleName("Admin");
        testRoleDTO.setUserIds(Collections.emptyList());
    }

    @Test
    public void createRole_ShouldReturnSavedRoleDTO() {
        when(rolesRepository.save(any(Roles.class))).thenReturn(testRole);

        RolesDTO savedRoleDTO = rolesService.createRole(testRoleDTO);

        assertThat(savedRoleDTO).isNotNull();
        assertThat(savedRoleDTO.getRoleId()).isEqualTo(1L);
        assertThat(savedRoleDTO.getRoleName()).isEqualTo("Admin");
    }

    @Test
    public void getRoleById_ShouldReturnRoleDTO_WhenRoleExists() {
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(testRole));

        RolesDTO foundRoleDTO = rolesService.getRoleById(1L);

        assertThat(foundRoleDTO).isNotNull();
        assertThat(foundRoleDTO.getRoleId()).isEqualTo(1L);
        assertThat(foundRoleDTO.getRoleName()).isEqualTo("Admin");
    }

    @Test
    public void getRoleById_ShouldReturnNull_WhenRoleDoesNotExist() {
        when(rolesRepository.findById(1L)).thenReturn(Optional.empty());

        RolesDTO foundRoleDTO = rolesService.getRoleById(1L);

        assertThat(foundRoleDTO).isNull();
    }

    @Test
    public void getAllRoles_ShouldReturnListOfRoleDTOs() {
        when(rolesRepository.findAll()).thenReturn(Arrays.asList(testRole));

        List<RolesDTO> rolesList = rolesService.getAllRoles();

        assertThat(rolesList).isNotEmpty();
        assertThat(rolesList.size()).isEqualTo(1);
        assertThat(rolesList.get(0).getRoleId()).isEqualTo(1L);
    }

    @Test
    public void deleteRole_ShouldCallRepositoryDelete() {
        rolesService.deleteRole(1L);

        verify(rolesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void convertToDTO_ShouldMapRoleToRoleDTO() {
        RolesDTO dto = rolesService.convertToDTO(testRole);

        assertThat(dto.getRoleId()).isEqualTo(testRole.getRoleId());
        assertThat(dto.getRoleName()).isEqualTo(testRole.getRoleName());
    }

    @Test
    public void convertToEntity_ShouldMapRoleDTOToRole() {
        Roles role = rolesService.convertToEntity(testRoleDTO);

        assertThat(role.getRoleId()).isEqualTo(testRoleDTO.getRoleId());
        assertThat(role.getRoleName()).isEqualTo(testRoleDTO.getRoleName());
    }
}
