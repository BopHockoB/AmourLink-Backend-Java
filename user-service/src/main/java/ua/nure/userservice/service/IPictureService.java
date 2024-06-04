package ua.nure.userservice.service;

import jakarta.transaction.Transactional;
import ua.nure.userservice.model.Picture;

import java.util.UUID;

public interface IPictureService {
    Picture createPicture(Picture picture);

    @Transactional
    void deletePicture(UUID pictureId);

    @Transactional
    void swapPositions(UUID positionId1, UUID positionId2, UUID userId);
}
