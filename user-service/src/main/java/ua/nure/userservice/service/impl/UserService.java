package ua.nure.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import ua.nure.userservice.exception.RoleNotFoundException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.Role;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.UserRepository;
import ua.nure.userservice.service.IRoleService;
import ua.nure.userservice.service.IUserService;


import java.util.List;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;


    private final IRoleService roleService;


    @Override
    public User findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User" + userId + " not found"));
    }

    @Override
    public User findUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User " + email + " not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User assignRole(String email, String roleName) {
        User user = findUser(email);

        return assignRole(user, roleName);
    }
    @Override
    public User assignRole(UUID userId, String roleName)  {
        User user = findUser(userId);

       return assignRole(user, roleName);
    }

    private User assignRole(User user, String roleName) {
        Role role = roleService.findRole(roleName);

        if (role != null)
            user.getRoles().add(role);
        else
            throw new RoleNotFoundException("Role " + roleName + " not found");

        return userRepository.save(user);
    }

    @Override
    public User unassignRole(UUID userId, String roleName) {
        User user = findUser(userId);

        return unassignRole(user, roleName);
    }

    @Override
    public User unassignRole(String email, String roleName) {
        User user = findUser(email);

        return unassignRole(user, roleName);
    }

    private User unassignRole(User user, String roleName) {
        Role role = roleService.findRole(roleName);

        if (role != null)
            user.getRoles().remove(role);
        else
            throw new RoleNotFoundException("Role " + roleName + " not found");

        return userRepository.save(user);
    }


}