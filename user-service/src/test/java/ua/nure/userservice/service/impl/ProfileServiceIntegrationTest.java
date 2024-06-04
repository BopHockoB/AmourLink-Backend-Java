//package ua.nure.userservice.service.impl;
//
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//import ua.nure.userservice.model.Picture;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class ProfileServiceIntegrationTest {
//
//    @Autowired
//    private ProfileService profileService;
//
//    private static String FILE_NAME = "images/1.png";
//
//    @BeforeEach
//    public void setUp() {
//    }
//
//    @Test
//    public void whenProfileService_thenFileUploadSuccess() throws IOException {
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        File file = new File(classloader.getResource(FILE_NAME).getFile());
//        assertTrue(file.exists());
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
//                IOUtils.toByteArray(input));
//        Picture picture = profileService.updateImage(1, multipartFile);
//
//        assertNotNull(picture);
//    }
//    //TODO
//}