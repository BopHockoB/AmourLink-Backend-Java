package ua.nure.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.model.Tag;
import ua.nure.userservice.model.dto.mapper.ProfileMapper;
import ua.nure.userservice.resolver.UserId;
import ua.nure.userservice.exception.PictureNotFoundException;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.dto.ProfileDTO;
import ua.nure.userservice.model.dto.TagDTO;
import ua.nure.userservice.responce.ResponseBody;
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

    @PostMapping("/add")
    public ResponseEntity<ResponseBody> add(@RequestBody @Valid ProfileDTO profileDTO, @UserId UUID userId){
        Profile profile = profileMapper.profileDtoToProfile(profileDTO);

        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDto(
                profileService.createProfile(profile, userId)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllUsers(){

        List<ProfileDTO> profileDTOList = profileMapper.profileListToProfileDtoList(
                profileService.findAllProfile()
        );

        ResponseBody responseBody = new ResponseBody(profileDTOList);
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody> getByUserId(@PathVariable("userId") UUID userId){
        ProfileDTO profileDTO = profileMapper.profileToProfileDto(
                profileService.findProfile(userId)
        );
        ResponseBody responseBody = new ResponseBody(profileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid ProfileDTO profileDTO){
        Profile profile = profileMapper.profileDtoToProfile(profileDTO);

        ProfileDTO resultProfileDTO = profileMapper.profileToProfileDto(
                profileService.updateProfile(profile)
        );

        ResponseBody responseBody = new ResponseBody(resultProfileDTO);
        return ResponseEntity.ok(responseBody);
    }


    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") UUID userId){
        profileService.deleteProfile(userId);
    }

    @PostMapping(
            path ="/add-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody> addImages(@RequestParam("file") MultipartFile file, @RequestParam("position") int position, @UserId UUID userId) throws PictureNotFoundException {
       ProfileDTO profileDTO = profileMapper.profileToProfileDto(
               profileService.addImageToProfile(position, file, userId)
       );

       ResponseBody responseBody = new ResponseBody(profileDTO);
       return ResponseEntity.ok(responseBody);

    }

    @PutMapping("/swap-pictures")
    public void swapPicturePositions(@RequestBody UUID pictureId1, @RequestBody UUID pictureId2, @UserId UUID userId){
        pictureService.swapPositions(pictureId1, pictureId2, userId);
    }

    @DeleteMapping("/delete-picture/{pictureId}")
    public void deletePicture(@PathVariable UUID pictureId) throws PictureNotFoundException {
        pictureService.deletePicture(pictureId);
    }

    @PostMapping("/add-tag")
    public ResponseEntity<ResponseBody> addTag(@RequestBody TagDTO tagDTO, @UserId UUID userId){
        Tag tag = profileMapper.tagDTOToTag(tagDTO);
        ProfileDTO profileDTO = profileMapper.profileToProfileDto(profileService.addTagToProfile(
                tag.getTagName(), userId
        ));

        ResponseBody responseBody = new ResponseBody(profileDTO);
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/unassign-tag/{tagId}")
    public void unassignTag(@PathVariable UUID tagId, @UserId UUID userId){
        profileService.unassignTag(tagId, userId);
        //TODO add trigger to automatically delete tag if nobody is assign
    }
}
