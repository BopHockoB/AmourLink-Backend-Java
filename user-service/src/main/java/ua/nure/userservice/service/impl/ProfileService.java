package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.client.MediaServiceClient;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.exception.ProfileNotFoundException;
import ua.nure.userservice.model.Picture;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.Tag;
import ua.nure.userservice.repository.ProfileRepository;
import ua.nure.userservice.repository.TagRepository;
import ua.nure.userservice.service.IProfileService;

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
    private final PictureService pictureService;
    private final TagRepository tagRepository;

    @Override
    public Profile createProfile(Profile profile, UUID userId) throws ProfileAlreadyExistsException {

        profile.setProfileId(userId);

        Optional<Profile> retrievedProfile = profileRepository.findById(userId);
        if (retrievedProfile.isPresent()){
            log.error("A user's {} profile already exists", userId);
            throw new ProfileAlreadyExistsException("A user's " + userId +" profile already exists");
        }
        log.info("Creating profile {}", profile.getProfileId());
        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        log.info("Updating profile {}", profile.getProfileId());
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public void deleteProfile(UUID userId) {
        log.info("Deleting profile {}", userId);
        profileRepository.deleteById(userId);
    }

    @Override
    public Profile findProfile(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile " + id + " not found"));
    }


    @Override
    public List<Profile> findAllProfile() {
        return profileRepository.findAll();
    }

    @Override
    public Picture addImageToProfile(int position, MultipartFile image, UUID userId) {

        Profile profile = this.findProfile(userId);
        log.info("Updating user's profile {} images", userId);


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

    @Override
    @Transactional
    public Profile addTagToProfile(String tagName, UUID userId) {
        Profile profile = findProfile(userId);

        Tag tag = tagRepository.findByTagName(tagName)
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setTagName(tagName);
                    return tagRepository.save(newTag);
                });

        profile.getTags().add(tag);
        return profileRepository.save(profile);
    }

}
