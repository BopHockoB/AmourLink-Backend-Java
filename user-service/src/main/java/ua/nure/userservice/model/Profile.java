package ua.nure.userservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import ua.nure.userservice.model.serializer.PointSerializer;

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

    private String firstname;
    private String lastname;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String bio;
    private Integer age;
    private Integer height;
    private String occupation;
    private String nationality;

//    @JsonSerialize(using = PointSerializer.class)
    @JsonIgnore
    private Point lastLocation;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private Degree degree;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Picture> pictures;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<InfoDetails> infoDetails;

    @ManyToMany()
    @JoinTable(
            name = "user_details_tag",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;

}
