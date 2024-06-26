package ua.nure.userservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoDetailsRequest {

    private UUID infoId;
    private UUID answerId;

}
