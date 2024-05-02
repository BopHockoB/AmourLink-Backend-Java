//package ua.nure.userservice.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//@FeignClient(name = "media-service", url = "${application.config.media-service-url}")
//public interface MediaServiceClient {
//
//    @PostMapping("/upload-image")
//    String uploadImage(@RequestParam("file") MultipartFile file);
//
//}
//
////TODO Create a RequestInterceptor