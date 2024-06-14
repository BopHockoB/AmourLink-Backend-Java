package ua.nure.securityservice.service;

import java.util.UUID;

public interface IActivationService {
    void activateUserAccount(UUID activationTokenId);

    void createActivationToken(String email);
}
