package ua.nure.mediamodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.mediamodule.service.ICloudinaryService;

import java.io.IOException;

@RestController
@RequestMapping("api/media-service/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ICloudinaryService cloudinaryService;

    @PostMapping(
            path ="/upload-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file) {
        try {
            String securedImageUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(securedImageUrl);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload image: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<String> deleteImage(@RequestParam("imageUrl") String imageUrl) {
        try {
            cloudinaryService.deleteImage(imageUrl);
            return ResponseEntity.ok("Image deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to delete image: " + e.getMessage());
        }
    }
}
