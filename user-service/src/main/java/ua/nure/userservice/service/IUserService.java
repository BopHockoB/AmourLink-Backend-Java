package ua.nure.userservice.service;

import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User createUser(User user) throws UserAlreadyExistsException;
    User updateUser(User user);
    User deleteUser(String email);
    User deleteUser(UUID id);
    User findUser(UUID userId);
    User findUser(String email) throws UserNotFoundException;
    List<User> findAllUsers();
}
