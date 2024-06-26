package ua.nure.userservice.controller.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DegreeRequest {
    private String degreeType;
    private String degreeName;
    private String schoolName;
}
