package ua.nure.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.userservice.model.Profile;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByUserUserId(UUID userId);
    Profile deleteProfileByProfileId(UUID profileId);
    Profile deleteProfileByUserUserId(UUID userId);
}
