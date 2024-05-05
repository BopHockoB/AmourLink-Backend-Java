package ua.nure.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.exception.ProfileAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.request.UploadImageRequest;
import ua.nure.userservice.service.IProfileService;


import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/user-service/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;


    @GetMapping("/get-all")
    public ResponseEntity<List<Profile>> getAllUsers(){
        return new ResponseEntity<>(profileService.findAllProfile(), HttpStatus.FOUND);
    }
    @PostMapping("/add")
    public ResponseEntity<Profile> add(@RequestBody Profile profile){
        try {
            return ResponseEntity.ok(profileService.createProfile(profile));
        } catch (ProfileAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{userId}")
    public Profile getByUserId(@PathVariable("userId") UUID userId){
        try {
            return profileService.findProfileByUserId(userId);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") UUID userId){
        profileService.deleteProfileByUserId(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<Profile> update(@RequestBody Profile profile){
        return ResponseEntity.ok(profileService.updateProfile(profile));
    }


    @PostMapping(
            path ="/update-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImages(@RequestParam("file") MultipartFile file, @RequestParam("position") int position){
        profileService.updateImage(position, file);
        return ResponseEntity.ok().build();
    }
}
