package ua.nure.userservice.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nure.userservice.model.User;
import ua.nure.userservice.security.jwt.JwtService;
import ua.nure.userservice.service.impl.UserService;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    private final String userName = "user";
    private final UUID userId = UUID.randomUUID();
    private final String userEmail = "user@example.com";
    private String token;
    private final String secret = "36763979244226452948404D635164546A576D5A7134D43777217A25432A462D";
    private final int expirationTime = 5000;

    @BeforeEach
    void setUp() {
        User user = new User(userId, userEmail, "password", Collections.emptySet());
        when(userService.findUser(userName)).thenReturn(user);

        jwtService = new JwtService(userService, secret, expirationTime);
        token = jwtService.generateToken(userName);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        assertNotNull(token, "Token should not be null");
        assertEquals(3, token.split("\\.").length, "Token should have three parts");
    }

    @Test
    void extractUsernameFromToken_ShouldReturnCorrectUsername() {
        String extractedUserName = jwtService.extractUsernameFromToken(token);
        assertEquals(userEmail, extractedUserName, "Extracted username should match input");
    }

    @Test
    void tokenExpirationDate_ShouldBeInTheFuture() {
        Date expirationDate = jwtService.extractExpirationTimeFromToken(token);
        assertTrue(expirationDate.after(new Date()), "Token expiration should be in the future");
    }

    @Test
    void validateToken_WhenGivenValidTokenAndUserDetails_ShouldReturnTrue() {
        when(userDetails.getUsername()).thenReturn(userEmail);

        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid, "Token should be valid when user details match the token information");
    }
}
