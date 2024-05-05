package ua.nure.userservice.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nure.userservice.security.jwt.JwtService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private static final Logger log = LoggerFactory.getLogger(JwtServiceTest.class);
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    private String userName = "user";
    private String token;
    private String secret = "36763979244226452948404D635164546A576D5A7134D43777217A25432A462D";
    private int expirationTime = 5000;

    @BeforeEach
    void setUp() {
        // Mock the environment variables
        System.setProperty("spring.jwt.secret", secret);
        System.setProperty("spring.jwt.jwtExpirationInMs", String.valueOf(expirationTime));

        // Initialize JwtService with mocked settings
        jwtService = new JwtService(secret, expirationTime);

        // Generate token for testing
        token = jwtService.generateToken(userName);
    }

    @Test
    void generateTokenTest() {
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // Check format
    }

    @Test
    void extractUsernameFromTokenTest() {
        String extractedUserName = jwtService.extractUsernameFromToken(token);
        assertEquals(userName, extractedUserName);
    }

    @Test
    void tokenExpirationDateTest() {
        Date expirationDate = jwtService.extractExpirationTimeFromToken(token);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void validateTokenTest_Success() {
        when(userDetails.getUsername()).thenReturn(userName);

        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);


    }


}
