package ua.nure.userservice.service.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.userservice.model.Info;

import java.util.UUID;

@Repository
public interface InfoRepository extends JpaRepository<Info, UUID> {
}
