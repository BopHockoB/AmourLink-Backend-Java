package ua.nure.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.userservice.model.Picture;
import ua.nure.userservice.model.Profile;

import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, UUID> {
    void deletePictureByPictureId(UUID pictureId);
}
