package ua.nure.subscriptionservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.subscriptionservice.model.User;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    User deleteByEmail(String email);
    User deleteUserByUserId(UUID userId);

    boolean existsByEmail(String email);
}
