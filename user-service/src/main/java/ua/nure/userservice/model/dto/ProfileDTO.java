package ua.nure.userservice.model.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.userservice.model.Gender;
import ua.nure.userservice.model.Language;
import ua.nure.userservice.model.Music;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO implements Serializable {

    private UUID id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstname;
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastname;
    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    private String bio;
    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;
    @Min(value = 110, message = "Height must be at least 110 cm")
    private Integer height;
    private String occupation;
    private String nationality;
    @NotNull(message = "Gender must be specified")
    private Gender gender;

    private MusicDTO music;
    private List<LanguageDTO> languages;
    private List<HobbyDTO> hobbies;
    private DegreeDTO degree;
    private List<PictureDTO> pictures;
    private List<InfoDTO> info;
    private List<TagDTO> tags;
}
