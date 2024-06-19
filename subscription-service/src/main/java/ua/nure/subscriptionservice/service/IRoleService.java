package ua.nure.subscriptionservice.service;


import ua.nure.subscriptionservice.model.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    Role findRole(UUID roleId);

    Role findRole(String roleName);

    List<Role> findAllRoles();


}
