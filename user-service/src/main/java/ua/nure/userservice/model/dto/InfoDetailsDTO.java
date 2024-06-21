package ua.nure.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.userservice.model.compositePk.InfoDetailsKey;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoDetailsDTO {

    private AnswerDTO answer;
    private InfoDTO info;
    private UUID id;
}
