package ua.nure.securityservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.securityservice.exception.ActivationTokenException;
import ua.nure.securityservice.responce.ResponseBody;
import ua.nure.securityservice.service.IActivationService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security-service/activation-token")
public class ActivationController {

    @Value("${application.activation-redirect.url}")
    private String redirectUrl;
    private final IActivationService activationService;

    @RequestMapping("/activate/{activationTokenId}")
    public void activate(@PathVariable("activationTokenId") UUID activationTokenId,
                         HttpServletResponse response) throws IOException {
        try {
            activationService.activateUserAccount(activationTokenId);

        } catch (ActivationTokenException e){
            response.sendRedirect(redirectUrl + "?Failure");
        }

        response.sendRedirect(redirectUrl + "?Success");
    }

    @PostMapping("/create-new-token")
    public ResponseEntity<ResponseBody> createNewToken(@RequestBody String email){
        activationService.createActivationToken(email);

        return ResponseEntity.ok(new ResponseBody("Successfully created new activation token"));
    }
}
