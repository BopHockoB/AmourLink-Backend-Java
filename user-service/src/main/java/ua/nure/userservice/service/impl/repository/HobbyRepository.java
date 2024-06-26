package ua.nure.userservice.service.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.userservice.model.Hobby;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, UUID> {
    Optional<Hobby> findByHobbyName(String name);
}
