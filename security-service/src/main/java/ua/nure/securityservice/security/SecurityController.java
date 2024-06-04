package ua.nure.securityservice.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.securityservice.security.jwt.JwtAuthenticationRequest;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.security.oauth2.GoogleTokenVerifierService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class SecurityController {
    private final JwtService jwtService;
    private final GoogleTokenVerifierService googleTokenVerifierService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String getTokenForAuthenticatedUser(@RequestBody JwtAuthenticationRequest authRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }
        else {
            throw new RuntimeException("Invalid user credentials");
        }
    }

    @PostMapping("/google")
    public String getTokenForGoogleUser(@RequestBody String token){
        return googleTokenVerifierService.getToken(token);
    }
}