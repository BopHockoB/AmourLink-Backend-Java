package ua.nure.userservice.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.userservice.model.Gender;
import ua.nure.userservice.model.Hobby;
import ua.nure.userservice.model.Language;


import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO implements Serializable {
    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")  // Assuming 18 is the minimum age to use the app
    private Integer age;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastname;

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;

    @Min(value = 110, message = "Height must be at least 110 cm")
    private Integer height;

    private String occupation;
    private String nationality;

    @NotNull(message = "Gender must be specified")
    private Gender gender;

}
