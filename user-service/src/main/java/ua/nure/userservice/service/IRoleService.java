package ua.nure.userservice.service;



import ua.nure.userservice.model.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    Role findRole(UUID roleId);

    Role findRole(String roleName);

    List<Role> findAllRoles();

    Role updateRole(Role role);

    void deleteRole(UUID roleId);
}
