package ua.nure.userservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageRequest {
    private Integer position;
    private File image;
}
