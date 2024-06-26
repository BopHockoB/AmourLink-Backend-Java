package ua.nure.userservice.service.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.userservice.model.Language;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Optional<Language> findByLanguageName(String name);
}
