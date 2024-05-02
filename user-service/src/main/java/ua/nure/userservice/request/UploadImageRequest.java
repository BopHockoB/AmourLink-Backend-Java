package ua.nure.userservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.userservice.model.Profile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageRequest {
    Integer position;
    MultipartFile image;
    Profile profile;
}
