package com.example.movie_studio.casts;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
public class CastsDto {
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "movieId cannot be null")
    private Integer movieId;

    @Column(nullable = false)
    @NotNull(message = "actorId cannot be null")
    private Long actorId;

    @Column(nullable = false)
    @NotBlank(message = "roleType cannot be null or empty")
    private String roleType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
