package ua.nure.securityservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.securityservice.exception.RoleNotFoundException;
import ua.nure.securityservice.exception.UserAlreadyExistsException;
import ua.nure.securityservice.exception.UserNotFoundException;
import ua.nure.securityservice.model.Role;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.repository.RoleRepository;
import ua.nure.securityservice.repository.UserRepository;
import ua.nure.securityservice.service.IRoleService;
import ua.nure.securityservice.service.IUserService;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ActivationService activationService;
    private final IRoleService roleService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public User createUser(User user) {
        Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());
        if (retrievedUser.isPresent()) {
            log.warn("User {} already exists", user.getEmail());
            throw new UserAlreadyExistsException("A user with email" + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        if (savedUser.getAccountType() == User.AccountType.LOCAL)
            activationService.createActivationToken(savedUser.getEmail());

        savedUser = assignRole(user, Role.RoleEnum.INCOMPLETE_USER.name());

        return savedUser;
    }


    @Override
    public User updateUser(User user) {
       return userRepository.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(String email) {
       return userRepository.deleteByEmail(email);
    }

    @Override
    @Transactional
    public User deleteUser(UUID userId) {
        return userRepository.deleteUserByUserId(userId);
    }

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