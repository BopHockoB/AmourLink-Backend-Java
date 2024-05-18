package ua.nure.userservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.locationtech.jts.geom.Point;
import ua.nure.userservice.model.dto.ProfileDTO;


import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_details")
public class Profile {

    @Id
    @Column(name = "user_details_id")
    private UUID profileId;

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

    @Column(columnDefinition = "TEXT", length = 1000)
    private String bio;

    @Min(value = 110, message = "Height must be at least 110 cm")
    private Integer height;

    private String occupation;
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "music_id")
//    @NotNull(message = "Music preference must be specified")
    private Music music;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender must be specified")
    private Gender gender;

    private Point last_location;

    //    @NotEmpty(message = "Languages spoken cannot be empty")
    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Language> languages;

    //    @NotEmpty(message = "Hobbies cannot be empty")
    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Hobby> hobbies;

    //    @NotEmpty(message = "Degrees cannot be empty")
    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Degree> degrees;

    //    @NotEmpty(message = "Pictures cannot be empty")
    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Picture> pictures;

    @ManyToMany()
    @JoinTable(
            name = "user_details_tag",
            joinColumns = {@JoinColumn(name = "user_details_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;

    public Profile(ProfileDTO profileDTO) {
        this.nationality = profileDTO.getNationality();
        this.occupation = profileDTO.getOccupation();
        this.height = profileDTO.getHeight();
        this.bio = profileDTO.getBio();
        this.lastname = profileDTO.getLastname();
        this.firstname = profileDTO.getFirstname();
        this.age = profileDTO.getAge();
        this.gender = profileDTO.getGender();
    }
}
