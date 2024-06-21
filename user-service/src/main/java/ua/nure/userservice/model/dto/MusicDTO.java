package ua.nure.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDTO {
    private UUID id;
    private String spotifyId;
    private String title;
    private String artistName;
}
