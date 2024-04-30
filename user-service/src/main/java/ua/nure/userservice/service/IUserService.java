package ua.nure.userservice.service;

import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;

import java.util.List;

public interface IUserService {

    User createUser(User user) throws UserAlreadyExistsException;
    User updateUser(User user);
    User deleteUser(String email);
    User deleteUser(long id);
    User findUser(long userId);
    User findUser(String email) throws UserNotFoundException;
    List<User> findAllUsers();
}
