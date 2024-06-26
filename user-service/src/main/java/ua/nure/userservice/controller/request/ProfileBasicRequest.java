package ua.nure.userservice.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import ua.nure.userservice.model.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileBasicRequest {

    private String firstname;
    private String lastname;
    private Integer age;
    private Integer height;
    private String nationality;
    private Gender gender;
}
