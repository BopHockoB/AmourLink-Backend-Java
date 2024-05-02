package ua.nure.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.model.Picture;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.User;
import ua.nure.userservice.request.UploadImageRequest;

import java.util.List;
import java.util.UUID;

public interface IProfileService {

    Profile createProfile(Profile profile) throws ProfileAlreadyExistsException;
    Profile updateProfile(Profile profile);
    Profile deleteProfile(UUID id);
    Profile deleteProfileByUserId(UUID userId);
    Profile findProfile(UUID id);
    Profile findProfileByUserId(UUID id);
    List<Profile> findAllProfile();
    Picture updateImage(UploadImageRequest request);
}

