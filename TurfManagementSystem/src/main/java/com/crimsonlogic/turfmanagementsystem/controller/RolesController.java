package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * RolesController handles role-related HTTP requests.
 * Author: Shradha
 */

import com.crimsonlogic.turfmanagementsystem.dto.RolesDTO;
import com.crimsonlogic.turfmanagementsystem.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles") // Base URL for the controller
public class RolesController {

    @Autowired
    private RolesService rolesService;

    //create the new role
    @PostMapping
    public ResponseEntity<RolesDTO> createRole(@RequestBody RolesDTO rolesDTO) {
        RolesDTO createdRole = rolesService.createRole(rolesDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }
//get role by id
    @GetMapping("/{roleId}")
    public ResponseEntity<RolesDTO> getRoleById(@PathVariable Long roleId) {
        RolesDTO role = rolesService.getRoleById(roleId);
        if (role != null) {
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//get role all roles
    
    @GetMapping
    public ResponseEntity<List<RolesDTO>> getAllRoles() {
        List<RolesDTO> roles = rolesService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }


//delete role
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        rolesService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
