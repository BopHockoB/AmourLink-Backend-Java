package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.nure.userservice.model.Picture;
import ua.nure.userservice.repository.PictureRepository;
import ua.nure.userservice.service.IPictureService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureService implements IPictureService {

    private final PictureRepository pictureRepository;

    @Override
    public Picture createPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    @Transactional
    public void deletePicture(UUID pictureId) {
        pictureRepository.deletePictureByPictureId(pictureId);
        log.info("Deleted picture with id: {}", pictureId);

    }

    @Override
    @Transactional
    public void swapPositions(UUID positionId1, UUID positionId2, UUID userId) {
        Picture picture1 = pictureRepository.findById(positionId1).orElse(null);
        Picture picture2 = pictureRepository.findById(positionId2).orElse(null);

        if (isValidPicture(picture1, userId) && isValidPicture(picture2, userId)){
            Integer tmpPosition = picture1.getPosition();
            picture1.setPosition(picture2.getPosition());
            picture2.setPosition(tmpPosition);

            pictureRepository.save(picture1);
            pictureRepository.save(picture2);
}
    }

    private boolean isValidPicture(Picture picture, UUID userId) {
        return picture != null
                && picture.getProfile().getProfileId().equals(userId)
                && picture.getPosition() != null;
    }
}
