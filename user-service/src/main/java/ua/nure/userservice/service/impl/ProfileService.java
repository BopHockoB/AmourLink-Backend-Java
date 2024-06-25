package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.client.MediaServiceClient;
import ua.nure.userservice.exception.PictureNotFoundException;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.exception.ProfileNotFoundException;
import ua.nure.userservice.exception.TagNotFoundException;
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
    public Profile updateProfile(Profile updateProfile) {
        log.info("Updating profile {}", updateProfile.getProfileId());
        return profileRepository.findById(updateProfile.getProfileId())
                .map(profile -> {
                    profile.setProfileId(updateProfile.getProfileId());
                    profile.setFirstname(updateProfile.getFirstname());
                    profile.setLastname(updateProfile.getLastname());
                    profile.setBio(updateProfile.getBio());
                    profile.setAge(updateProfile.getHeight());
                    profile.setOccupation(updateProfile.getOccupation());
                    profile.setNationality(updateProfile.getNationality());
                    profile.setGender(updateProfile.getGender());
                    profile.setMusic(updateProfile.getMusic());
                    profile.setLanguages(updateProfile.getLanguages());
                    profile.setHobbies(updateProfile.getHobbies());
                    profile.setDegree(updateProfile.getDegree());
                    profile.setPictures(updateProfile.getPictures());
                    profile.setInfoDetails(updateProfile.getInfoDetails());
                    profile.setTags(updateProfile.getTags());
                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile " + updateProfile.getProfileId() + " not found"));

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
    public Profile addImageToProfile(int position, MultipartFile image, UUID userId) throws PictureNotFoundException {

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


        return picture.getProfile();
    }

    @Override
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

    @Override
    public void unassignTag(UUID tagId, UUID userId){
        Profile profile = profileRepository.findById(userId).orElseThrow(()->{
            log.warn("Tag {} not found", tagId);
            return new TagNotFoundException("Tag" + tagId + " not found");
        });

        profile.getTags().remove(tagId);
        profileRepository.save(profile);
    }
}
