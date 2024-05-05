package ua.nure.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.model.User;
import ua.nure.userservice.security.jwt.JwtService;
import ua.nure.userservice.service.impl.UserService;

@EnableFeignClients
@SpringBootApplication
public class UserServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(UserServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            UserService userService,
            JwtService jwtService
    ) {
        return args -> {
            var admin = User.builder()
                    .email("admin@mail.com")
                    .password("password")
                    .build();
            try {
                userService.createUser(admin);
            } catch (UserAlreadyExistsException e) {
                log.warn("Mock user {} already exists", admin.getEmail());
            }
            finally {
                System.out.println("Admin token: " + jwtService.generateToken(admin.getEmail()));
            }

        };
    }
    //Creates and register a mock user to generate an admin jwt token that can be used for test authorization
}
