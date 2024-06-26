package ua.nure.securityservice.security.oauth2;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nure.securityservice.exception.AccountTypeException;
import ua.nure.securityservice.exception.UserNotFoundException;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.responce.AuthenticationResponse;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.service.IUserService;
import ua.nure.securityservice.service.impl.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
public class GoogleTokenVerifierService {
    private final GoogleIdTokenVerifier verifier;
    private final IUserService userService;
    private final JwtService jwtService;

    @Autowired
    public GoogleTokenVerifierService(@Value("${google.client.client-id}") String clientId,
                                      UserService userService,
                                      JwtService jwtService) {
       verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
       this.userService = userService;
       this.jwtService = jwtService;
    }

    /**
     * Retrieves the token for the given Google ID token string.
     *
     * @param idTokenString The Google ID token string to retrieve the token for.
     * @return The token for the given Google ID token string.
     */
    public AuthenticationResponse getToken(String idTokenString) throws AccountTypeException {
        log.info("Getting token for idTokenString: {}", idTokenString);

        var payload = verify(idTokenString);
        User user = findUser(payload);

        if (user == null) {
            log.info("User not found, creating new user");

            user = User.builder()
                    .accountType(User.AccountType.GOOGLE)
                    .enabled(true)
                    .email(payload.getEmail())
                    .password(UUID.randomUUID().toString())
                    .build();
            user = userService.createUser(user);
        }

        if (user.getAccountType() != User.AccountType.GOOGLE) {
            log.error("User account type does not match {} ", User.AccountType.GOOGLE);
            throw new AccountTypeException(
                    "User " + user.getEmail() + " account type doesn't match to " + User.AccountType.GOOGLE
            );
        }

        return  AuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();


    }

    public GoogleIdToken.Payload verify(String idTokenString) {
        log.debug("Verifying idTokenString: {}", idTokenString);

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                log.error("Invalid ID token");
                throw new GeneralSecurityException("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            log.error("Could not verify token", e);
            throw new RuntimeException("Could not verify token", e);
        }
    }

    /**
     * Finds a user based on the given payload.
     *
     * @param payload The GoogleIdToken.Payload object containing user information.
     * @return The found User object if found, or null if the user is not found.
     */
    public User findUser(GoogleIdToken.Payload payload) {
        String userEmail = payload.getEmail();
        log.debug("Finding user with email: {}", userEmail);

        try {
            return userService.findUser(userEmail);
        } catch (UserNotFoundException e) {
            log.error("User not found with email: {}", userEmail, e);
            return null;
        }
    }

}