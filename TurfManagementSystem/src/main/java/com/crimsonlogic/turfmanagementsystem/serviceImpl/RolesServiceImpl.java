package com.crimsonlogic.turfmanagementsystem.serviceImpl;
//author:shradha
//roles calculations
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.turfmanagementsystem.dto.RolesDTO;
import com.crimsonlogic.turfmanagementsystem.entity.Roles;
import com.crimsonlogic.turfmanagementsystem.entity.Users;
import com.crimsonlogic.turfmanagementsystem.repository.RolesRepository;
import com.crimsonlogic.turfmanagementsystem.repository.UsersRepository; // Import your UserRepository
import com.crimsonlogic.turfmanagementsystem.service.RolesService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository userRepository; // Inject UserRepository

    //create a role
    @Override
    public RolesDTO createRole(RolesDTO rolesDTO) {
        Roles role = convertToEntity(rolesDTO);
        role = rolesRepository.save(role);
        return convertToDTO(role);
    }
//get role by id
    @Override
    public RolesDTO getRoleById(Long roleId) {
        Optional<Roles> role = rolesRepository.findById(roleId);
        return role.map(this::convertToDTO).orElse(null);
    }

    
    // get all the roles
    @Override
    public List<RolesDTO> getAllRoles() {
        return rolesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //delete the roles
    @Override
    public void deleteRole(Long roleId) {
        rolesRepository.deleteById(roleId);
    }

    //convert the dto
    public RolesDTO convertToDTO(Roles role) {
        RolesDTO rolesDTO = new RolesDTO();
        rolesDTO.setRoleId(role.getRoleId());
        rolesDTO.setRoleName(role.getRoleName());
        // Assuming you want to map user IDs
        rolesDTO.setUserIds(role.getUsers().stream()
                .map(Users::getUserId) // Assuming Users has a getUserId method
                .collect(Collectors.toList()));
        return rolesDTO;
    }

    
    //convert to entity
    public Roles convertToEntity(RolesDTO rolesDTO) {
        Roles role = new Roles();
        role.setRoleId(rolesDTO.getRoleId()); // Set roleId if it's coming from an update
        role.setRoleName(rolesDTO.getRoleName());

        // Convert user IDs to Users entities
        List<Users> users = userListFromUserIds(rolesDTO.getUserIds());
        role.setUsers(users); // Set the users in the role

        return role;
    }

    //list all the users in that role
    public List<Users> userListFromUserIds(List<String> list) {
        return list.stream()
                .map(userId -> userRepository.findById(userId) // Fetch each user by ID
                        .orElse(null)) // Handle user not found
                .filter(user -> user != null) // Remove any nulls
                .collect(Collectors.toList());
    }



	
}
