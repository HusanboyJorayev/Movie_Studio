package com.example.movie_studio.actor;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorDto {

    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2,max = 30)
    private String name;

    @Column(nullable = false)
    @Size(min = 3,max = 10)
    private String gender;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private Integer yearOfBirth;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
