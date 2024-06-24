package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Degree {
    @Id
    @GeneratedValue
    @Column(name = "degree_id")
    private UUID degreeId;
    @Column(nullable = false,
            length = 50)
    private String degreeType;
    @Column(nullable = false,
            length = 50)
    private String degreeName;
    @Column(nullable = false,
            length = 50)
    private String schoolName;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private Profile profile;
}
