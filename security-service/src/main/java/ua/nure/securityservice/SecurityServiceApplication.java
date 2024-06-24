package ua.nure.securityservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ua.nure.securityservice.exception.UserAlreadyExistsException;
import ua.nure.securityservice.model.Role;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.security.jwt.JwtService;
import ua.nure.securityservice.service.IUserService;

@Slf4j
@SpringBootApplication
@EnableFeignClients
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
                    .enabled(true)
                    .accountType(User.AccountType.LOCAL)
                    .build();
            try {
                userService.createUser(admin);
                userService.unassignRole(admin.getEmail() ,Role.RoleEnum.INCOMPLETE_USER.name());
            } catch (UserAlreadyExistsException e) {
                log.warn("Mock user {} already exists", admin.getEmail());
            } finally {

                userService.assignRole(admin.getEmail(), Role.RoleEnum.ADMIN.name());
                System.out.println("Admin token: " + jwtService.generateToken(admin.getEmail()));
            }

        };
    }
}
