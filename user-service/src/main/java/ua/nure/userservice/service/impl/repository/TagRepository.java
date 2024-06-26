package ua.nure.userservice.service.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.userservice.model.Tag;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByTagName(String name);
}
