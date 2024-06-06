package ua.nure.securityservice.security.oauth2;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nure.securityservice.client.FacebookClient;
import ua.nure.securityservice.exception.AccountTypeException;
import ua.nure.securityservice.exception.UserNotFoundException;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.service.impl.UserService;

import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor()
public class FacebookTokenVerifierService {

    private final FacebookClient facebookClient;
    private final UserService userService;
    private final JwtService jwtService;

    @Value("${facebook.app.id}")
    private String appId;

    @Value("${facebook.app.secret}")
    private String appSecret;

    public String getToken(String userAccessToken) throws GeneralSecurityException, AccountTypeException {
        String userEmail = verify(userAccessToken);
        User user = findUser(userEmail);

        if (user == null) {

            user = User.builder()
                    .accountType(User.AccountType.FACEBOOK)
                    .email(userEmail)
                    .password(UUID.randomUUID().toString())
                    .build();

            user = userService.createUser(user); // Assuming User has a constructor that accepts an email.
        }

        if (user.getAccountType() != User.AccountType.FACEBOOK)
            throw new AccountTypeException(
                    "User " + userEmail + " account type doesn't match to " + User.AccountType.FACEBOOK
            );

        return jwtService.generateToken(user.getEmail());
    }

    public String verify(String userAccessToken) throws GeneralSecurityException {
        // Forming the access token and url
        String accessToken = appId + "|" + appSecret;
        String debugTokenUrl = "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s";
        String completeUrl = String.format(debugTokenUrl, userAccessToken, accessToken);

        Map<String, Object> response = facebookClient.verifyToken(userAccessToken, accessToken);
            JSONObject jsonObject = new JSONObject(response);

            // Error checks on JSON object retrieval
            if (!jsonObject.containsKey("data")) {
                throw new GeneralSecurityException("Data not present in response");
            }

            JSONObject dataObject = (JSONObject) jsonObject.get("data");

            if (!dataObject.containsKey("is_valid") || !(Boolean) dataObject.get("is_valid")) {
                throw new GeneralSecurityException("Invalid ID token.");
            }

            if (!dataObject.containsKey("email")) {
                throw new GeneralSecurityException("Email not present in response");
            }

            return dataObject.getAsString("email");


    }

    public User findUser(String userEmail){
        try {
            User user = userService.findUser(userEmail);
            return user;
        } catch (UserNotFoundException e) {
            return null;
        }
    }
}