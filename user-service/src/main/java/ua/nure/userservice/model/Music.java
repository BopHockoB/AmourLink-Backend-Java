package ua.nure.userservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Music {

    @Id
    @GeneratedValue
    private UUID musicId;
    @Column(nullable = false)
    private String spotifyId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String artistName;
}
