package ua.nure.securityservice.service;



import ua.nure.securityservice.exception.UserAlreadyExistsException;
import ua.nure.securityservice.exception.UserNotFoundException;
import ua.nure.securityservice.model.User;

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
