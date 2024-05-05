package ua.nure.mediamodule.service.impl;

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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testDeleteImage() throws IOException {
        // Skip this test if no image was uploaded
        if (uploadedImageUrl == null) {
            return;
        }

        // Get the public ID of the uploaded image
        String publicId = cloudinaryService.retrievePublicId(uploadedImageUrl);
        // Delete the image
        cloudinaryService.deleteImage(uploadedImageUrl);

        // Attempt to fetch the image details from Cloudinary
        Map<String, Object> deletedImageDetails;
        try {
            deletedImageDetails = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            // If the image is successfully deleted, attempting to fetch its details should throw an exception
            deletedImageDetails = null;
        }

        // Assert that the image details cannot be fetched (indicating deletion)
        assertNull(deletedImageDetails);
    }
}
