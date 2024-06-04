package ua.nure.userservice.model;


import jakarta.persistence.*;
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
    @Column(name = "user_id")
    private UUID profileId;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String bio;
    private Integer age;
    private String firstname;
    private String lastname;
    private Integer height;
    private String occupation;
    private String nationality;
    private Point last_location;
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;


    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Language> languages;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Hobby> hobbies;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Degree> degrees;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Picture> pictures;

    @ManyToMany()
    @JoinTable(
            name = "user_details_tag",
            joinColumns = {@JoinColumn(name = "user_id")},
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
