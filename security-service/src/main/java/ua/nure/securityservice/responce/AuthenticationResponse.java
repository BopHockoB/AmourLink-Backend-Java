package ua.nure.securityservice.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse implements Serializable {
    private String accessToken;
    private String refreshToken;

}
