package ua.nure.securityservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.nure.securityservice.exception.UserAlreadyExistsException;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.service.IUserService;

@Slf4j
@SpringBootApplication
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(
            IUserService userService,
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
            } finally {
                System.out.println("Admin token: " + jwtService.generateToken(admin.getEmail()));
            }

        };
    }
}
