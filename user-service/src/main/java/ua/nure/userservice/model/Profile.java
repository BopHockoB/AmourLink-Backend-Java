package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

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
    @GeneratedValue
    @Column(name = "user_details_id")
    private UUID profileId;
    private Integer age;
    private String firstname;
    private String lastname;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String bio;
    private Integer height;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private String occupation;
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Float lastLocationLongitude;
    private Float lastLocationLatitude;

    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Language> languages;

    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Hobby> hobbies;

    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Degree> degrees;

    @OneToMany
    @JoinColumn(name = "user_details_id")
    private List<Picture> pictures;
}
