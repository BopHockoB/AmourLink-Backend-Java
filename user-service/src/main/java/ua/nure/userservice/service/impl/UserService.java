package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.userservice.exception.InvalidPasswordException;
import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.UserRepository;
import ua.nure.userservice.service.IUserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());
        if (retrievedUser.isPresent()) {
            log.warn("User {} already exists", user.getEmail());
            throw new UserAlreadyExistsException("A user with email" + user.getEmail() + " already exists");
        }
        if (!passwordIsValid(user.getPassword())){
            log.warn("User {} password is not valid", user.getEmail());
            throw new InvalidPasswordException("User " + user.getEmail() + " password is not valid");
        }
        //TODO add validation handling mechanism

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private boolean passwordIsValid(String password) {
        String regex = "^[a-zA-Z0-9!@#$%^&*()-_=+{};:,.<>?`~]*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
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
    public User deleteUser(UUID id) {
        return userRepository.deleteUserByUserId(id);
    }

    @Override
    public User findUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User" + id + " not found"));
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
}