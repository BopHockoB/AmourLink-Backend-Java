package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Picture createPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Transactional
    public void deletePicture(UUID pictureId) {
        pictureRepository.deletePictureByPictureId(pictureId);
        log.info("Deleted picture with id: {}", pictureId);

    }
}
