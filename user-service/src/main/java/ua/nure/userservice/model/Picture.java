package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import ua.nure.userservice.service.impl.ProfileService;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Picture {

    @Id
    @GeneratedValue
    @JdbcType(VarcharJdbcType.class)
    private UUID pictureId;
    private String pictureUrl;
    private Date timeAdded;
    private Integer position; // Represents a position in pictures sequence, possible values 1-6
    @ManyToOne
    @JoinColumn(name = "user_details_id")
    private Profile profile;
}
