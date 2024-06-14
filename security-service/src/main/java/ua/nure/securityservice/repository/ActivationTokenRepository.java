package ua.nure.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.securityservice.model.ActivationToken;
import ua.nure.securityservice.model.User;

import java.util.UUID;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, UUID> {
    boolean existsByUser(User user);

    void deleteByUser(User user);
}
