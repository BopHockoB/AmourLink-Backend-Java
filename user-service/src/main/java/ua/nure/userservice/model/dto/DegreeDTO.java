package ua.nure.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DegreeDTO {
    private UUID id;
    private String schoolName;
    private String degreeType;
    private Date startYear;
}
