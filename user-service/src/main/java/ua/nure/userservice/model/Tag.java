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
public class Tag {

    @Id
    @GeneratedValue
    UUID tagId;

    @Column(unique = true,
            nullable = false,
            length = 50)
    String tagName;

    @ManyToMany(mappedBy = "tags")
    List<Profile> profiles;
}
