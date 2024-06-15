package ua.nure.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.userservice.model.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicInfoDTO  {
    private String firstname;
    private String lastname;
    private String nationality;
    private int age;
    private Gender gender;
}
