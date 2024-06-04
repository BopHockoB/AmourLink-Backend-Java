package ua.nure.securityservice.security.oauth2;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nure.securityservice.exception.UserNotFoundException;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.service.IUserService;
import ua.nure.securityservice.service.impl.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

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
    public String getToken(String idTokenString) {
        var payload = verify(idTokenString);
        User user = findUser(payload);
        if (user == null)
            user = userService.createUser(user);

        return jwtService.generateToken(user.getEmail());
    }

    /**
     * Verifies the given Google ID token string and retrieves the payload.
     *
     * @param idTokenString The Google ID token string to verify.
     * @return The payload of the Google ID token.
     * @throws RuntimeException If the token could not be verified.
     */
    public GoogleIdToken.Payload verify(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                throw new GeneralSecurityException("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Could not verify token", e);
        }

    }


    /**
     * Finds a user based on the Google ID token payload.
     *
     * @param payload The Google ID token payload.
     * @return The found user or null if the user is not found.
     */
    public User findUser(GoogleIdToken.Payload payload){
        String userEmail = payload.getEmail();

        try {
            User user = userService.findUser(userEmail);
            return user;
        } catch (UserNotFoundException e) {
            return null;
        }

    }

}