package ua.nure.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.controller.request.*;
import ua.nure.userservice.model.Degree;
import ua.nure.userservice.model.Tag;
import ua.nure.userservice.model.dto.mapper.ProfileMapper;
import ua.nure.userservice.resolver.UserId;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.dto.ProfileDTO;
import ua.nure.userservice.model.dto.TagDTO;
import ua.nure.userservice.controller.responce.ResponseBody;
import ua.nure.userservice.service.IPictureService;
import ua.nure.userservice.service.IProfileService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/user-service/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;
    private final IPictureService pictureService;
    private final ProfileMapper profileMapper;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseBody> add(@RequestBody ProfileBasicRequest request,
                                            @UserId UUID userId){
        Profile profile = Profile.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .age(request.getAge())
                .height(request.getHeight())
                .nationality(request.getNationality())
                .gender(request.getGender())
                .build();

        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.createProfile(profile, userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-basic")
    public ResponseEntity<ResponseBody> updateBasic(@RequestBody ProfileBasicRequest request,
                                            @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.updateBasicProfile(request, userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-bio")
    public ResponseEntity<ResponseBody> updateBio(@RequestBody FieldUpdateRequest request,
                                                    @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.updateBio(request.getValue(), userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-occupation")
    public ResponseEntity<ResponseBody> updateOccupation(@RequestBody FieldUpdateRequest request,
                                                  @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.updateOccupation(request.getValue(), userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-last-location")
    public ResponseEntity<ResponseBody> updateLastLocation(@RequestBody LocationRequest request,
                                                         @UserId UUID userId) {
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.updateLastLocation(request.getLongitude(), request.getLatitude(), userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/update-degree")
    public ResponseEntity<ResponseBody> updateDegree(@RequestBody DegreeRequest request,
                                                           @UserId UUID userId) {
        Degree degree = Degree.builder()
                .degreeType(request.getDegreeType())
                .degreeName(request.getDegreeName())
                .schoolName(request.getSchoolName())
                .build();
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.updateDegree(degree, userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/add-hobby")
    public ResponseEntity<ResponseBody> addHobby(@RequestBody FieldUpdateRequest request,
                                                         @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.addHobby(request.getValue(), userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @PutMapping("/unassign-hobby/{hobbyId}")
    public void unassignHobby(@PathVariable UUID hobbyId,
                            @UserId UUID userId){
        profileService.unassignHobby(hobbyId, userId);
        //TODO add trigger to automatically delete if nobody is assign
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/add-language")
    public ResponseEntity<ResponseBody> addLanguage(@RequestBody FieldUpdateRequest request,
                                                 @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.addLanguage(request.getValue(), userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping("/add-info-details")
    public ResponseEntity<ResponseBody> addInfoDetails(@RequestBody InfoDetailsRequest request,
                                                    @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.addInfoDetails(request, userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/delete-info-details/{infoId}")
    public ResponseEntity<ResponseBody> deleteInfoDetails(@PathVariable UUID infoId,
                                                       @UserId UUID userId){
        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.deleteInfoDetails(infoId, userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @PutMapping("/unassign-language/{languageId}")
    public void unassignLanguage(@PathVariable UUID languageId,
                              @UserId UUID userId){
        profileService.unassignLanguage(languageId, userId);
        //TODO add trigger to automatically delete if nobody is assign
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllUsers(){

        List<ProfileDTO> profileDTOList = profileMapper.profileListToProfileDTOList(
                profileService.findAllProfile()
        );

        ResponseBody responseBody = new ResponseBody(profileDTOList);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "{#userId == #userIdToCheck and hasAnyRole('ROLE_USER', 'ROLE_PREMIUM_USER')}")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody> getByUserId(@PathVariable("userId") UUID userId,
                                                    @UserId UUID userIdToCheck){
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(
                profileService.findProfile(userId)
        );
        ResponseBody responseBody = new ResponseBody(profileDTO);
        return ResponseEntity.ok(responseBody);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "{#userId == #profileDTO.id and hasAnyRole('ROLE_USER', 'ROLE_PREMIUM_USER')}")
    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid ProfileDTO profileDTO,
                                               @UserId UUID userId){
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);

        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDTO(
                profileService.updateProfile(profile)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") UUID userId){
        profileService.deleteProfile(userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @PostMapping(
            path ="/add-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody> addImages(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("position") int position,
                                                  @UserId UUID userId) {
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(
                profileService.addImage(position, file, userId)
        );

        ResponseBody responseBody = new ResponseBody(profileDTO);
        return ResponseEntity.ok(responseBody);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @PutMapping("/swap-pictures")
    public void swapPicturePositions(@RequestBody PictureSwapRequest pictureSwapRequest,
                                     @UserId UUID userId){
        pictureService.swapPositions(
                pictureSwapRequest.getFirstPictureId(),
                pictureSwapRequest.getSecondPictureId(),
                userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @DeleteMapping("/delete-picture/{pictureId}")
    public void deletePicture(@PathVariable UUID pictureId) {
        pictureService.deletePicture(pictureId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @PostMapping("/add-tag")
    public ResponseEntity<ResponseBody> addTag(@RequestBody TagDTO tagDTO,
                                               @UserId UUID userId){
        Tag tag = profileMapper.tagDTOToTag(tagDTO);
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(profileService.addTag(
                tag.getTagName(), userId
        ));

        ResponseBody responseBody = new ResponseBody(profileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_PREMIUM_USER')")
    @PutMapping("/unassign-tag/{tagId}")
    public void unassignTag(@PathVariable UUID tagId,
                            @UserId UUID userId){
        profileService.unassignTag(tagId, userId);
        //TODO add trigger to automatically delete tag if nobody is assign
    }


}
