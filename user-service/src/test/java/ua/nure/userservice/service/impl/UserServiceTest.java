package ua.nure.userservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // This is where we could set up additional configurations if necessary
    }

    @Test
    void testCreateUserSuccess() {
        User newUser = User.builder()
                .email("john.doe@example.com")
                .password("password123")
                .build();
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testCreateUserFailure() {
        User newUser = User.builder()
                .email("john.doe@example.com")
                .password("password123")
                .build();
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(newUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(newUser));
    }

    @Test
    void testUpdateUser() {
        User existingUser = User.builder()
                .email("jane.doe@example.com")
                .password("password123")
                .build();
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(existingUser);

        assertNotNull(updatedUser);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUserByEmail() {
        String email = "john.doe@example.com";
        when(userRepository.deleteByEmail(email)).thenReturn(new User());

        User deletedUser = userService.deleteUser(email);

        assertNotNull(deletedUser);
        verify(userRepository, times(1)).deleteByEmail(email);
    }

    @Test
    void testFindUserByIdSuccess() {
        UUID userId = UUID.randomUUID();
        User foundUser = User.builder()
                .email("john.doe@example.com")
                .password("password123")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(foundUser));

        User result = userService.findUser(userId);

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    void testFindUserByIdFailure() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUser(userId));
    }

    @Test
    void testFindUserByEmailSuccess() {
        String email = "jane.doe@example.com";
        User foundUser = User.builder()
                .email("jane.doe@example.com")
                .password("password123")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(foundUser));

        User result = userService.findUser(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testFindUserByEmailFailure() {
        String email = "jane.doe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUser(email));
    }
}