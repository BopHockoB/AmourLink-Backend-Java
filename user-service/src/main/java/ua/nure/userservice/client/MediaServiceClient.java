package ua.nure.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.client.feignConfig.FeignConfig;

@FeignClient(
        name = "media-service",
        url = "${application.config.media-service-url}",
        configuration = FeignConfig.class)
public interface MediaServiceClient {

    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadImage(@RequestPart("file") MultipartFile file);

    @DeleteMapping("/delete-image")
    String deleteImage(@RequestParam("imageId") String imageId);
}


//TODO Create a RequestInterceptor