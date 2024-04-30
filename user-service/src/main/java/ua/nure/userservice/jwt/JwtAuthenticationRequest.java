package ua.nure.userservice.jwt;

import lombok.Data;

@Data
public class JwtAuthenticationRequest {
    private String userName;
    private String password;
}