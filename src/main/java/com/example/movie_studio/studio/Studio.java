package com.example.movie_studio.studio;

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
@Table(name = "studio")
@NoArgsConstructor
@AllArgsConstructor
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    private String city;
    // todo uniqe - founded
    private Integer founded;
    private String companyType;

    @OneToMany(mappedBy = "studioId", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    List<Movie> movies;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
