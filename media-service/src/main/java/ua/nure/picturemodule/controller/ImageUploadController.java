package ua.nure.picturemodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.picturemodule.Response.ImageUploadResponse;
import ua.nure.picturemodule.service.ICloudinaryService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ICloudinaryService cloudinaryService;

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            String securedImageUrl = cloudinaryService.uploadImage(image);
            return ResponseEntity.ok(securedImageUrl);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload image: " + e.getMessage());
        }
    }
}
