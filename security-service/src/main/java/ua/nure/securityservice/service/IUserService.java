package ua.nure.securityservice.service;


import ua.nure.securityservice.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User createUser(User user);
    User updateUser(User user);
    User deleteUser(String email);
    User deleteUser(UUID id);
    User findUser(UUID userId);
    User findUser(String email);
    List<User> findAllUsers();
    void activateUserAccount(UUID activationTokenId);

    User assignRole(String email, String roleName);
    User assignRole(UUID userId, String roleName);
    User unassignRole(UUID userId, String roleName);

    User unassignRole(String email, String roleName);
}
