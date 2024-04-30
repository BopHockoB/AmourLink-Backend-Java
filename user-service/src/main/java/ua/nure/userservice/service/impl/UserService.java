package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.UserRepository;
import ua.nure.userservice.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());
        if (retrievedUser.isPresent()){
            throw new UserAlreadyExistsException("A user with email" +user.getEmail() +" already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
    public User deleteUser(long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public User findUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User findUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}