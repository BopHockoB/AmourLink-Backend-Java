package ua.nure.subscriptionservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.subscriptionservice.model.Role;
import ua.nure.subscriptionservice.service.impl.repository.RoleRepository;
import ua.nure.subscriptionservice.service.IRoleService;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null)
            log.error("Role {} not found", roleId);
        return role;
    }

    @Override
    public Role findRole(String roleName){
        Role role = roleRepository.findByRoleName(roleName).orElse(null);
        if (role == null){
            log.error("Role {} not found", roleName);
        }
        return role;
    }

    @Override
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

}
