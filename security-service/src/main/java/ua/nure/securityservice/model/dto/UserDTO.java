package ua.nure.securityservice.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @Email
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_=+{};:,.<>?`~]*$", message = "Password is not valid")
    private String password;
}