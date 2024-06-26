package ua.nure.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.controller.request.InfoDetailsRequest;
import ua.nure.userservice.exception.PictureNotFoundException;
import ua.nure.userservice.model.Degree;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.controller.request.ProfileBasicRequest;

import java.util.List;
import java.util.UUID;


public interface IProfileService {

    Profile createProfile(Profile profile, UUID userId);
    Profile updateProfile(Profile profile);
    void deleteProfile(UUID id);
    Profile findProfile(UUID id);
    List<Profile> findAllProfile();


    Profile updateBasicProfile(ProfileBasicRequest request, UUID userId);
    Profile updateBio(String bio, UUID userId);
    Profile updateOccupation(String occupation, UUID userId);
    Profile updateDegree(Degree degree, UUID userId);
    Profile updateLastLocation(double longitude, double latitude, UUID userId);

    Profile addImage(int position, MultipartFile image, UUID userId) throws PictureNotFoundException;
    Profile addTag(String tagName, UUID userId);
    Profile addHobby(String hobbyName, UUID userId);
    Profile addLanguage(String value, UUID userId);
    Profile addInfoDetails(InfoDetailsRequest request, UUID userId);

    void unassignHobby(UUID hobbyId, UUID userId);
    void unassignTag(UUID tagId, UUID userId);
    void unassignLanguage(UUID languageId, UUID userId);


    Profile deleteInfoDetails(UUID infoId, UUID userId);
}

