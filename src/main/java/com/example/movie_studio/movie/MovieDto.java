package com.example.movie_studio.movie;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto {

    @Column(nullable = false)
    private Integer id;

    @NotNull(message = "directorId cannot be null")
    private Integer directorId;

    @NotNull(message = "studioId cannot be null")
    private Integer studioId;

    @NotBlank(message = "name cannot be null or empty")
    private String name;

    @NotBlank(message = "countryOfRelease cannot be null or empty")
    private String countryOfRelease;

    @NotBlank(message = "language cannot be null or empty")
    private String language;

    @NotBlank(message = "filmingLocation cannot be null or empty")
    private String filmingLocation;

    @NotNull(message = "yearOfRelease cannot be null")
    private Integer yearOfRelease;

    @NotBlank(message = "category cannot be null or empty")
    private String category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
