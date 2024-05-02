package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
//import ua.nure.userservice.client.MediaServiceClient;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.exception.ProfileNotFoundException;
import ua.nure.userservice.model.Picture;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.User;
import ua.nure.userservice.repository.ProfileRepository;
import ua.nure.userservice.request.UploadImageRequest;
import ua.nure.userservice.service.IProfileService;
import ua.nure.userservice.util.SecurityUtil;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

//    private final MediaServiceClient mediaServiceClient;
    private final ProfileRepository profileRepository;
    private final UserService userService;

    @Override
    public Profile createProfile(Profile profile) throws ProfileAlreadyExistsException {
        Optional<Profile> retrievedUser = profileRepository.findByUserUserId(profile.getUser().getUserId());
        if (retrievedUser.isPresent()){
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

    public Picture updateImage(UploadImageRequest uploadImageRequest) {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();

        if (userDetails == null)
            throw new SecurityException("User not authenticated");

        User user = userService.findUser(userDetails.getUsername());

        Profile profile = findProfile(user.getUserId());
        log.info("Updating user's profile {} images", user.getUserId());

        String imageUrl = null; //mediaServiceClient.uploadImage(uploadImageRequest.getImage());
        Picture picture = Picture.builder()
                .pictureUrl(imageUrl)
                .timeAdded(new Date(System.currentTimeMillis()))
                .position(uploadImageRequest.getPosition())
                .build();

        profile.setPictures(profile.getPictures().stream()
                .filter(p -> p.getPosition().equals(uploadImageRequest.getPosition()))
                .toList());

        profile.getPictures().add(picture);
        log.info("Added new picture {} to position {}", imageUrl, picture.getPosition());

        updateProfile(profile);
        return picture;
    }

}
