package ua.nure.userservice.model.compositePk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class InfoDetailsKey {
    @Column(name = "answer_id")
    private UUID answerId;

    @Column(name = "info_id")
    private UUID infoId;

    @Column(name = "user_id")
    private UUID userId;
}
