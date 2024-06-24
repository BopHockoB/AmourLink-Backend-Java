package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private UUID userId;

    @Column(unique = true,
    nullable = false,
    length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    private Set<Role> roles;

    public enum AccountType{
        LOCAL,
        GOOGLE,
        FACEBOOK
    }

}