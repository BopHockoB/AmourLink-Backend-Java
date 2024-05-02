package ua.nure.picturemodule.config;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(@Value("${cloudinary.cloud-name}") String cloud_name,
                                 @Value("${cloudinary.api-key}") String api_key,
                                 @Value("${cloudinary.api-secret}") String api_secret) {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);

        log.info("Cloudinary was configured: {}", config);
        return new Cloudinary(config);

    }
}
