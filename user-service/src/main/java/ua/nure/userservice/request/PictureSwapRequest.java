package ua.nure.userservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureSwapRequest {
    private UUID firstPictureId;
    private UUID secondPictureId;
}
