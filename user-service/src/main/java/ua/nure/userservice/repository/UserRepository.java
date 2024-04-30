package ua.nure.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User deleteByEmail(String email);
    User deleteById(long id);
}
