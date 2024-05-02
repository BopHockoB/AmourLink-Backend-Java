package ua.nure.picturemodule.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CloudinaryServiceIntegrationTest {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private Cloudinary cloudinary;

    private String uploadedImageUrl;

    @BeforeEach
    public void setUp() {
        uploadedImageUrl = null;
    }

    @Test
    public void testUploadImage() throws IOException {
        // Create a temporary file to upload
        String path = "C:\\Users\\asus\\IdeaProjects\\AmourLink-BackEnd-Java\\media-service\\src\\test\\resources\\images\\1.png";
        File file = new File(path);
        MultipartFile multipartFile = new MockMultipartFile("file", "1.png", "image/png", Files.readAllBytes(file.toPath()));

        // Call the method to test
        uploadedImageUrl = cloudinaryService.uploadImage(multipartFile);

        // Assert that the URL is not null
        assertNotNull(uploadedImageUrl);
    }

    @Test
    public void testUploadedImageUrl() throws Exception {
        // Skip this test if no image was uploaded
        if (uploadedImageUrl == null) {
            return;
        }

        // Fetch the image details from Cloudinary
        Map<String, Object> imageDetails = cloudinary.api().resource(uploadedImageUrl, ObjectUtils.emptyMap());

        // Assert that the image details are fetched successfully
        assertNotNull(imageDetails);

        // Assert that the fetched URL matches the uploaded URL
        assertEquals(uploadedImageUrl, imageDetails.get("secure_url"));
    }
}
