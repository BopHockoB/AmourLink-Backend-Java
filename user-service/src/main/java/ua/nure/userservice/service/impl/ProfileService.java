package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.client.MediaServiceClient;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.exception.ProfileNotFoundException;
import ua.nure.userservice.model.Picture;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.ProfileRepository;
import ua.nure.userservice.service.IProfileService;
import ua.nure.userservice.util.SecurityUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final MediaServiceClient mediaServiceClient;
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final PictureService pictureService;

    @Override
    public Profile createProfile(Profile profile) throws ProfileAlreadyExistsException {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User user = userService.findUser(userDetails.getUsername());
        profile.setUser(user);

        Optional<Profile> retrievedProfile = profileRepository.findByUserUserId(user.getUserId());
        if (retrievedProfile.isPresent()){
            log.error("A user's {} profile already exists", profile.getUser().getUserId());
            throw new ProfileAlreadyExistsException("A user's " + profile.getUser().getUserId() +" profile already exists");
        }
        log.info("Creating profile {}", profile.getUser().getUserId());
        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        log.info("Updating profile {}", profile.getUser().getUserId());
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public Profile deleteProfile(UUID id) {
        log.info("Deleting profile {}", id);

        return profileRepository.deleteProfileByProfileId(id);
    }

    @Override
    @Transactional
    public Profile deleteProfileByUserId(UUID userId) {
        log.info("Deleting user's profile {}", userId);
        return profileRepository.deleteProfileByUserUserId(userId);
    }

    @Override
    public Profile findProfile(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile" + id + " not found"));
    }

    @Override
    public Profile findProfileByUserId(UUID userId) throws ProfileNotFoundException {
        return profileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("User" + userId + " does not have profile"));
    }

    @Override
    public List<Profile> findAllProfile() {
        return profileRepository.findAll();
    }

    @Override
    public Picture updateImage(int position, MultipartFile image) {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        if (userDetails == null)
            throw new SecurityException("User not authenticated");

        User user = userService.findUser(userDetails.getUsername());
        Profile profile = findProfileByUserId(user.getUserId());
        log.info("Updating user's profile {} images", user.getUserId());


        String imageUrl = mediaServiceClient.uploadImage(image);
        Picture picture = Picture.builder()
                .pictureUrl(imageUrl)
                .timeAdded(new Date(System.currentTimeMillis()))
                .position(position)
                .profile(profile)
                .build();

        // Remove the existing picture at the same position if exists
        Picture pictureToDelete = profile.getPictures().stream()
                .filter(p -> p.getPosition().equals(position))
                .findFirst().orElse(null);

        if (pictureToDelete != null) {

            pictureService.deletePicture(pictureToDelete.getPictureId());
            mediaServiceClient.deleteImage(pictureToDelete.getPictureUrl());
        }
        pictureService.createPicture(picture);
        log.info("Added new picture {} to position {}", imageUrl, picture.getPosition());


        return picture;
    }


}
