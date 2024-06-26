package ua.nure.securityservice.service;

import ua.nure.securityservice.model.ActivationToken;

import java.util.UUID;

public interface IActivationService {

    ActivationToken findActivationToken(UUID activationTokenId);

    void createActivationToken(String email);

    void deleteActivationToken(UUID activationTokenId);
}
