package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hobby {

    @Id
    @GeneratedValue
    private UUID hobbyId;

    @Column(nullable = false,
            length = 50)
    private String hobbyName;

    @ManyToMany(mappedBy = "hobbies")
    List<Profile> profiles;
}
