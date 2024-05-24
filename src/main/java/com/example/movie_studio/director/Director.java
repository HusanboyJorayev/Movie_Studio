package com.example.movie_studio.director;

import com.example.movie_studio.movie.Movie;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "director")
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String gender;
    private String placeBirth;
    private String country;
    private Integer yearBirth;

    @OneToMany(mappedBy = "directorId",fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    List<Movie>movies;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
