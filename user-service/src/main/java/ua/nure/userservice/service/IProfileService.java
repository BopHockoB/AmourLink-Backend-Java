package ua.nure.userservice.service;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.exception.PictureNotFoundException;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.model.Profile;

import java.util.List;
import java.util.UUID;


public interface IProfileService {

    Profile createProfile(Profile profile, UUID userId) throws ProfileAlreadyExistsException;
    Profile updateProfile(Profile profile);
    void deleteProfile(UUID id);
    Profile findProfile(UUID id);
    List<Profile> findAllProfile();
    Profile addImageToProfile(int position, MultipartFile image, UUID userId) throws PictureNotFoundException;

    @Transactional
    Profile addTagToProfile(String tagName, UUID userId);

    void unassignTag(UUID tagId, UUID userId);
}

