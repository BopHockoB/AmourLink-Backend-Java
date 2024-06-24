package ua.nure.userservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

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



    @Column(nullable = false, length = 50)
    private String firstname;
    @Column(length = 50)
    private String lastname;

    @Column(length = 500)
    private String bio;

    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer height;

    private String occupation;

    private String nationality;

//    @JsonSerialize(using = PointSerializer.class)
    @JsonIgnore
    @Column(nullable = false,
            columnDefinition = "POINT")
    private Point lastLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;


    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;


    @ManyToMany
    @JoinTable(
            name = "user_details_language",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "language_id", nullable = false)}
    )
    private List<Language> languages;

    @OneToMany
    @JoinColumn(name = "user_id", nullable = false)
    private List<Hobby> hobbies;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @PrimaryKeyJoinColumn
    private Degree degree;

    @OneToMany
    @JoinColumn(name = "user_id", nullable = false)
    @NotBlank
    private List<Picture> pictures;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<InfoDetails> infoDetails;

    @ManyToMany
    @JoinTable(
            name = "user_details_tag",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false)}
    )
    private List<Tag> tags;


}
