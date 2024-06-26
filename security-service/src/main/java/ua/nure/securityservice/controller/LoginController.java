package ua.nure.securityservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.securityservice.exception.AccountTypeException;
import ua.nure.securityservice.request.RefreshRequest;
import ua.nure.securityservice.responce.AuthenticationResponse;
import ua.nure.securityservice.responce.ResponseBody;
import ua.nure.securityservice.security.jwt.JwtAuthenticationRequest;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.security.oauth2.FacebookTokenVerifierService;
import ua.nure.securityservice.security.oauth2.GoogleTokenVerifierService;

import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security-service/login")
public class LoginController {
    private final JwtService jwtService;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final FacebookTokenVerifierService facebookTokenVerifierService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<ResponseBody> getTokenForAuthenticatedUser(@RequestBody JwtAuthenticationRequest authRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            AuthenticationResponse authenticationResponse =
                    AuthenticationResponse.builder()
                            .accessToken(jwtService.generateToken(authRequest.getEmail()))
                            .refreshToken(jwtService.generateRefreshToken(authRequest.getEmail()
                            ))
                            .build();

            return ResponseEntity.ok(new ResponseBody(authenticationResponse));
        }
        else {
            throw new RuntimeException("Invalid user credentials");
        }
    }

    @PostMapping("/google")
    public ResponseEntity<ResponseBody> getTokenForGoogleUser(@RequestBody String token) throws AccountTypeException {
        return ResponseEntity.ok(new ResponseBody(googleTokenVerifierService.getToken(token)));
    }

    @PostMapping("/facebook")
    public ResponseEntity<ResponseBody> getTokenForFacebookUser(@RequestBody String token) throws AccountTypeException, GeneralSecurityException {
        return ResponseEntity.ok(new ResponseBody(facebookTokenVerifierService.getToken(token)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseBody> refreshToken(
            @RequestBody RefreshRequest refreshRequest
    ) {
        ResponseBody responseBody = new ResponseBody(
                jwtService.refreshToken(refreshRequest.getRefreshToken())
        );
        return ResponseEntity.ok(responseBody);
    }
}