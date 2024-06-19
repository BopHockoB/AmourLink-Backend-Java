package ua.nure.userservice.service;



import ua.nure.userservice.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {



    User findUser(UUID userId);
    User findUser(String email);
    List<User> findAllUsers();

    User assignRole(String email, String roleName);
    User assignRole(UUID userId, String roleName);

    User unassignRole(UUID userId, String roleName);
    User unassignRole(String email, String roleName);
}
