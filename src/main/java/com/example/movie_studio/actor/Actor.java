package com.example.movie_studio.actor;

import com.example.movie_studio.casts.Casts;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer codes;
    private String gender;
    private String nationality;
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "actorId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Casts> casts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
