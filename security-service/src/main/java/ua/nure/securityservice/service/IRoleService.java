package ua.nure.securityservice.service;

import ua.nure.securityservice.model.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    Role findRole(UUID roleId);

    Role findRole(String roleName);

    List<Role> findAllRoles();

    Role updateRole(Role role);

    void deleteRole(UUID roleId);
}
