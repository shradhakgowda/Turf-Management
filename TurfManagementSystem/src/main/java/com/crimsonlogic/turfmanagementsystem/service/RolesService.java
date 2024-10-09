package com.crimsonlogic.turfmanagementsystem.service;

import com.crimsonlogic.turfmanagementsystem.dto.RolesDTO;

import java.util.List;

public interface RolesService {
    RolesDTO createRole(RolesDTO rolesDTO);
    RolesDTO getRoleById(Long roleId);
    List<RolesDTO> getAllRoles();
   // RolesDTO updateRole(Long roleId, RolesDTO rolesDTO,String roleName);
    void deleteRole(Long roleId);
}
