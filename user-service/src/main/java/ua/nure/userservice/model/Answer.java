package ua.nure.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "info_answer")
public class Answer {
    @Id @GeneratedValue
    private UUID answerId;

    @Column(nullable = false, length = 200)
    private String answer;

}
