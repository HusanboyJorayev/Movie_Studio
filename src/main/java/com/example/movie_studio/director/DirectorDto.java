package com.example.movie_studio.director;

import com.example.movie_studio.movie.Movie;
import com.example.movie_studio.movie.MovieDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectorDto {
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "name cannot be null or empty")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "gender cannot be null or empty")
    private String gender;

    @Column(nullable = false)
    @NotBlank(message = "place birth cannot be null or empty")
    private String placeBirth;

    @Column(nullable = false)
    @NotBlank(message = "country cannot be null or empty")
    private String country;

    @Column(nullable = false)
    @NotNull(message = "year of birth cannot be null")
    private Integer yearBirth;

    List<MovieDto> movies;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
