package ua.nure.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.userservice.model.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    User deleteByEmail(String email);
    User deleteUserByUserId(UUID userId);
}
