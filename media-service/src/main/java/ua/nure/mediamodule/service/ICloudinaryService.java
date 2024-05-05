package ua.nure.mediamodule.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICloudinaryService {

    String uploadImage(MultipartFile file) throws IOException;
    boolean deleteImage(String imageId) throws IOException;
}
