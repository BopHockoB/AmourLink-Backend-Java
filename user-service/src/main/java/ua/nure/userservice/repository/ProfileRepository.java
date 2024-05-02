package ua.nure.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.userservice.model.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserUserId(UUID userId);
    Profile deleteProfileByProfileId(UUID profileId);
    Profile deleteProfileByUserUserId(UUID userId);
}
