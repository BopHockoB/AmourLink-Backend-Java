package ua.nure.userservice.service.impl.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.userservice.model.User;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    User deleteByEmail(String email);
    User deleteUserByUserId(UUID userId);
}
