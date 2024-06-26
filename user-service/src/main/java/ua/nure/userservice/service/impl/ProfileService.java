package ua.nure.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.client.MediaServiceClient;
import ua.nure.userservice.controller.request.InfoDetailsRequest;
import ua.nure.userservice.exception.*;
import ua.nure.userservice.model.*;
import ua.nure.userservice.model.compositePk.InfoDetailsKey;
import ua.nure.userservice.service.impl.repository.LanguageRepository;
import ua.nure.userservice.service.impl.repository.ProfileRepository;
import ua.nure.userservice.service.impl.repository.TagRepository;
import ua.nure.userservice.controller.request.ProfileBasicRequest;
import ua.nure.userservice.service.IProfileService;
import ua.nure.userservice.service.impl.repository.HobbyRepository;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final MediaServiceClient mediaServiceClient;
    private final ProfileRepository profileRepository;
    private final PictureService pictureService;
    private final TagRepository tagRepository;
    private final HobbyRepository hobbyRepository;
    private final LanguageRepository languageRepository;
    private final InfoService infoService;

    @Override
    public Profile createProfile(Profile profile, UUID userId) {

        profile.setProfileId(userId);

        Optional<Profile> retrievedProfile = profileRepository.findById(userId);
        if (retrievedProfile.isPresent()) {
            log.error("A user's {} profile already exists", userId);
            throw new ProfileAlreadyExistsException("A user's " + userId + " profile already exists");
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
    public Profile addImage(int position, MultipartFile image, UUID userId) throws PictureNotFoundException {

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
    public Profile addTag(String tagName, UUID userId) {
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
    public void unassignTag(UUID tagId, UUID userId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(() -> {
            log.warn("Tag {} not found", tagId);
            return new TagNotFoundException("Tag" + tagId + " not found");
        });

        List<Tag> filteredTags = profile.getTags().stream()
                .filter(tag -> !tag.getTagId().equals(tagId))
                .collect(Collectors.toList());

        profile.setTags(filteredTags);
        profileRepository.save(profile);
    }

    @Override
    public void unassignLanguage(UUID languageId, UUID userId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(() -> {
            log.warn("Language {} not found", languageId);
            return new LanguageNotFoundException("Language " + languageId + " not found");
        });

        List<Language> filteredLanguages = profile.getLanguages().stream()
                .filter(language -> !language.getLanguageId().equals(languageId))
                .collect(Collectors.toList());

        profile.setLanguages(filteredLanguages);
        profileRepository.save(profile);
    }

    @Override
    public Profile deleteInfoDetails(UUID infoId, UUID userId) {
        Profile profile = findProfile(userId);

        // You need to implement findInfoDetails method in InfoDetailsRepository
        List<InfoDetails> infoDetailsList = infoService.findInfoDetailsByUserId(userId);

        if(infoDetailsList != null && profile != null) {
            List<InfoDetails> filteredList = infoDetailsList.stream()
                    .filter(infoDetails -> !infoDetails.getInfo().getInfoId().equals(infoId))
                    .collect(Collectors.toList());

            profile.setInfoDetails(filteredList);

            return profileRepository.save(profile);
        } else {
            throw new NoSuchElementException("Unable to find Profile or InfoDetails by given id"); }
    }

    @Override
    public Profile updateBasicProfile(ProfileBasicRequest request, UUID userId) {
        log.info("Updating profile {} basic info", userId);
        return profileRepository.findById(userId)
                .map(profile -> {
                    profile.setFirstname(request.getFirstname());
                    profile.setLastname(request.getLastname());
                    profile.setAge(request.getAge());
                    profile.setAge(request.getHeight());
                    profile.setNationality(request.getNationality());
                    profile.setGender(request.getGender());
                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile " + userId + " not found"));

    }

    @Override
    public Profile updateBio(String bio, UUID userId) {
        log.info("Updating profile {} bio", userId);
        return profileRepository.findById(userId)
                .map(profile -> {
                    profile.setBio(bio);
                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile " + userId + " not found"));
    }

    @Override
    public Profile updateOccupation(String occupation, UUID userId) {
        log.info("Updating profile {} occupation", userId);
        return profileRepository.findById(userId)
                .map(profile -> {
                    profile.setOccupation(occupation);
                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile " + userId + " not found"));
    }

    @Override
    public Profile updateDegree(Degree degree, UUID userId) {

        Profile profile = findProfile(userId);
        degree.setProfile(profile);
        degree.setDegreeId(userId);
        profile.setDegree(degree);

        profileRepository.save(profile);
        return profile;

    }

    @Override
    public Profile updateLastLocation(double longitude, double latitude, UUID userId) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point lastLocation = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        return profileRepository.findById(userId)
                .map(profile -> {
                    profile.setLastLocation(lastLocation);
                    return profileRepository.save(profile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile " + userId + " not found"));

    }

    @Override
    public Profile addHobby(String hobbyName, UUID userId) {
        Profile profile = findProfile(userId);
        Hobby hobby = hobbyRepository.findByHobbyName(hobbyName)
                .orElseGet(() -> {
                    Hobby newHobby = new Hobby();
                    newHobby.setHobbyName(hobbyName);
                    return hobbyRepository.save(newHobby);
                });

        profile.getHobbies().add(hobby);
        return profileRepository.save(profile);
    }

    @Override
    public Profile addLanguage(String languageName, UUID userId) {
        Profile profile = findProfile(userId);
        Language language = languageRepository.findByLanguageName(languageName)
                .orElseThrow(() -> {
                    log.warn("Language {} not found", languageName);
                    return new LanguageNotFoundException("Language " + languageName + "not found");
                });

        profile.getLanguages().add(language);
        return profileRepository.save(profile);
    }

    public Profile addInfoDetails(InfoDetailsRequest request, UUID userId) {
        Profile profile = findProfile(userId);
        Info info = infoService.findInfoById(request.getInfoId());
        Answer answer = retrieveAnswerFromInfo(info, request.getAnswerId());

        Optional<InfoDetails> optionalInfoDetails = profile.getInfoDetails().stream()
                .filter(infoDetails -> infoDetails.getInfo().equals(info))
                .findFirst();

        if (optionalInfoDetails.isPresent()) {
            assignAnswerToInfoDetails(optionalInfoDetails.get(), info, answer);
        } else {
            // add Info to list
            InfoDetails newInfoDetails = new InfoDetails();

            newInfoDetails.setInfoDetailsId(new InfoDetailsKey(
                    answer.getAnswerId(),
                    info.getInfoId(),
                    userId));

            assignAnswerToInfoDetails(newInfoDetails, info, answer);

            newInfoDetails.setProfile(profile);
            profile.getInfoDetails().add(newInfoDetails);
        }
        return profileRepository.save(profile);
    }

    private void assignAnswerToInfoDetails(InfoDetails infoDetails, Info info, Answer answer) {
        infoDetails.setInfo(info);
        infoDetails.setAnswer(answer);
    }

    private Answer retrieveAnswerFromInfo(Info info, UUID answerId) {
        Optional<Answer> optionalAnswer = info.getAnswers().stream()
                .filter(answer -> answer.getAnswerId().equals(answerId))
                .findFirst();

        return optionalAnswer.orElseThrow(() -> {
            log.warn("Answer {} not found in Info {}", answerId, info.getInfoId());
            return new IllegalStateException("Answer " + answerId + " not found in Info " + info.getInfoId());
        });
    }

    @Override
    public void unassignHobby(UUID hobbyId, UUID userId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(() -> {
            log.warn("Hobby {} not found", hobbyId);
            return new HobbyNotFoundException("hobby" + hobbyId + " not found");
        });

        List<Hobby> filteredHobbies = profile.getHobbies().stream()
                .filter(hobby -> !hobby.getHobbyId().equals(hobbyId))
                .collect(Collectors.toList());

        profile.setHobbies(filteredHobbies);
        profileRepository.save(profile);
    }


}
