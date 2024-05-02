package ua.nure.picturemodule.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ICloudinaryService {

    String uploadImage(MultipartFile file) throws IOException;
}
