package ua.nure.securityservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ua.nure.securityservice.exception.ActivationTokenException;
import ua.nure.securityservice.exception.UserNotFoundException;
import ua.nure.securityservice.model.ActivationToken;
import ua.nure.securityservice.model.EmailDetails;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.repository.ActivationTokenRepository;
import ua.nure.securityservice.repository.UserRepository;
import ua.nure.securityservice.service.IActivationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivationService implements IActivationService {


    private final RoleService roleService;
    @Value("${application.host}")
    private String host;
    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, EmailDetails> kafkaTemplate;

    @Override
    public ActivationToken findActivationToken(UUID activationTokenId) {
        ActivationToken activationToken = activationTokenRepository.findById(activationTokenId).orElseThrow(() -> {
            log.error("Activation {} token is not found", activationTokenId);
            return new ActivationTokenException("Activation " + activationTokenId + " token not found");
        });
        if (!tokenNotExpired(activationToken)) {
            log.warn("Activation token {} is expired", activationTokenId);
            deleteActivationToken(activationTokenId);
            createActivationToken(activationToken.getUser().getEmail());
            throw new ActivationTokenException("Activation " + activationTokenId + " token is expired, new activation token was sent");
        }
        return activationToken;
    }

    private boolean tokenNotExpired(ActivationToken activationToken) {
        return activationToken.getExpirationDate().after(new Date());
    }


    @Override
    public void createActivationToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->{
            log.warn("User {} is not found", email);
            return new UserNotFoundException("User " + email + " not found");
                }
        );

        if (user.isEnabled()){
            log.warn("User {} is already enabled", email);
            throw new ActivationTokenException("User " + email + " is already enabled");
        }

        //User can have only one ActivationToken, so the old one been deleted if exists
        if (activationTokenRepository.existsByUser(user))
            activationTokenRepository.deleteByUser(user);

        //Token is valid only for 10 min
        ActivationToken activationToken = ActivationToken.builder()
                .user(user)
                .expirationDate(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .build();
        activationToken = activationTokenRepository.save(activationToken);


        // Initialize email details
        String fromEmail = "noreply@amourlink.com"; // Change as per your requirements
        String subject = "Account Activation";
        String body = String.format("""
                <p>Dear User,</p>
                <br>
                                    <p>Please click on the following link to activate your account:</p>
                                    <br><br>
                                    <p><a href="http://%s/api/security-service/activation-token/activate/%s">Activate account</a></p>
                                    <br><br>
                                    <p>Thank you,</p>
                                    <br>
                                    <p>Amourlink Team.</p>""", host, activationToken.getActivationTokenId());
        String toEmail = user.getEmail();

        // Create EmailDetails Object
        EmailDetails emailDetails = new EmailDetails(fromEmail, toEmail, subject, body, null);

        // Send email details using Kafka Template
        kafkaTemplate.send("email-service-send-email", emailDetails);

    }

    @Override
    public void deleteActivationToken(UUID activationTokenId) {
        activationTokenRepository.deleteById(activationTokenId);
    }
}
