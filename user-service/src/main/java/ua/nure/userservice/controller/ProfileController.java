package ua.nure.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.resolver.UserId;
import ua.nure.userservice.exception.PictureNotFoundException;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.model.dto.ProfileDTO;
import ua.nure.userservice.model.dto.TagDTO;
import ua.nure.userservice.responce.ResponseBody;
import ua.nure.userservice.service.IPictureService;
import ua.nure.userservice.service.IProfileService;

import java.util.UUID;


@RestController
@RequestMapping("api/user-service/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;
    private final IPictureService pictureService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllUsers(){

        ResponseBody responseBody = new ResponseBody(profileService.findAllProfile());
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseBody> add(@RequestBody @Valid ProfileDTO profileDTO, @UserId UUID userId){
        Profile profile = new Profile(profileDTO);

        ResponseBody responseBody = new ResponseBody(profileService.createProfile(profile, userId));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody> getByUserId(@PathVariable("userId") UUID userId){
        ResponseBody responseBody = new ResponseBody(profileService.findProfile(userId));
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") UUID userId){
        profileService.deleteProfile(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid ProfileDTO profileDTO){
        Profile profile = new Profile(profileDTO);

        ResponseBody responseBody = new ResponseBody(profileService.updateProfile(profile));
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping(
            path ="/add-picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addImages(@RequestParam("file") MultipartFile file, @RequestParam("position") int position, @UserId UUID userId) throws PictureNotFoundException {
        profileService.addImageToProfile(position, file, userId);
    }

    @PutMapping("/swap-pictures")
    public ResponseEntity<ResponseBody> swapPicturePositions(@RequestBody UUID pictureId1, @RequestBody UUID pictureId2, @UserId UUID userId){
        pictureService.swapPositions(pictureId1, pictureId2, userId);
        return ResponseEntity.ok(new ResponseBody());
    }

    @DeleteMapping("/delete-picture")
    public void deletePicture(@RequestParam("pictureId") UUID pictureId) throws PictureNotFoundException {
        pictureService.deletePicture(pictureId);
    }

    @PostMapping("/add-tag")
    public ResponseEntity<ResponseBody> addTag(@RequestBody TagDTO tagDTO, @UserId UUID userId){
        ResponseBody responseBody = new ResponseBody(profileService.addTagToProfile(tagDTO.getTagName(), userId));
        return ResponseEntity.ok(responseBody);
    }
}
