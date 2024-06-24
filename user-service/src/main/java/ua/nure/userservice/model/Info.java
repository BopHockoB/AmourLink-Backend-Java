package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Info {
    @Id
    @GeneratedValue
    private UUID infoId;


    @Column(unique = true,
            nullable = false,
            length = 250)
    private String title;

    @OneToMany
    @JoinColumn(name = "info_id")
    private List<Answer> answers;
}
