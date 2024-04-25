package com.example.movie_studio.casts;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "casts")
@NoArgsConstructor
@AllArgsConstructor
public class Casts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer movieId;
    private Long actorId;
    private String roleType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
