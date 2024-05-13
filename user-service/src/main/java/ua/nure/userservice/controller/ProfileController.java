package ua.nure.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.model.Profile;
import ua.nure.userservice.responce.ResponseBody;
import ua.nure.userservice.service.IProfileService;


import java.util.UUID;


@RestController
@RequestMapping("api/user-service/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final IProfileService profileService;


    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllUsers(){

        ResponseBody responseBody = new ResponseBody(profileService.findAllProfile());
        return new ResponseEntity<>(responseBody, HttpStatus.FOUND);
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseBody> add(@RequestBody @Valid Profile profile){
        ResponseBody responseBody = new ResponseBody(profileService.createProfile(profile));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody> getByUserId(@PathVariable("userId") UUID userId){
        ResponseBody responseBody = new ResponseBody(profileService.findProfileByUserId(userId));
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") UUID userId){
        profileService.deleteProfileByUserId(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid Profile profile){
        ResponseBody responseBody = new ResponseBody(profileService.updateProfile(profile));
        return ResponseEntity.ok(responseBody);
    }


    @PostMapping(
            path ="/add-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addImages(@RequestParam("file") MultipartFile file, @RequestParam("position") int position){
        profileService.addImageToProfile(position, file);
    }

    @PostMapping("/add-tag")
    public ResponseEntity<ResponseBody> addTag(@RequestBody String tag){
        ResponseBody responseBody = new ResponseBody(profileService.addTagToProfile(tag));
        return ResponseEntity.ok(responseBody);
    }
}
