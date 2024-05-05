package ua.nure.mediamodule.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.mediamodule.service.ICloudinaryService;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String secureUrl = (String) uploadResult.get("secure_url");
        log.info("New image uploaded: {}", secureUrl);
        return secureUrl;

        //TODO add params to uploader
    }

    public boolean deleteImage(String url) throws IOException{

        String publicId = retrievePublicId(url);

        Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        if (deleteResult != null && deleteResult.containsKey("result") && deleteResult.get("result").equals("ok")) {
            log.info("Image deleted successfully: {}", publicId);
            return true;
        } else {
            log.error("Failed to delete image: {}", publicId);
            return false;
        }
    }

    public String retrievePublicId(String imageUrl) {
        // Split the image URL by the '/' character and get the last part, which represents the public ID
        String[] parts = imageUrl.split("/");
        return parts[parts.length - 1];
    }
}
