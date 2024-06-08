package ua.nure.subscriptionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.subscriptionservice.model.Subscription;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findByUserId(UUID userId);

}

