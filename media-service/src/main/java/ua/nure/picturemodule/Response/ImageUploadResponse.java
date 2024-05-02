package ua.nure.picturemodule.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.client.loadbalancer.Response;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadResponse {
    private String SecureUrl;
    private Date createdAt;

}
