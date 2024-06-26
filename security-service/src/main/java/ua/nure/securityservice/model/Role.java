package ua.nure.securityservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "roleId")
@Entity
public class Role {
    @Id @GeneratedValue
    UUID roleId;
    String roleName;

    public enum RoleEnum{
        ADMIN,
        INCOMPLETE_USER,
        USER,
        PREMIUM_USER
    }
}
