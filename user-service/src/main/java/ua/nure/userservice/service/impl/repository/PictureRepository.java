package ua.nure.userservice.service.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.userservice.model.Picture;

import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {
    void deletePictureByPictureId(UUID pictureId);
}
