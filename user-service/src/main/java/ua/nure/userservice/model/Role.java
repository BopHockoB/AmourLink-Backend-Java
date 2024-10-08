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
public class Role {
    @Id @GeneratedValue
    UUID roleId;
    @Column(unique = true,
    nullable = false,
    length = 50)
    String roleName;

    public enum RoleEnum{
        ADMIN,
        INCOMPLETE_USER,
        USER,
        PREMIUM_USER
    }
}
