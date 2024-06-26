package ua.nure.userservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.userservice.model.Gender;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileInfoRequest {
    private UUID id;
    private String firstname;
    private String lastname;
    private String nationality;
    private int age;
    private Gender gender;
}
