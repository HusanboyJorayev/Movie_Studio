package com.example.movie_studio.actor;

import com.example.movie_studio.casts.Casts;
import com.example.movie_studio.casts.CastsDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActorDto {

    //@Column(nullable = false)
    private Long id;

    //@Column(nullable = false)
    @Size(min = 2, max = 30)
    @NotBlank(message = "name cannot be null or empty")
    private String name;

    //@Column(nullable = false, unique = true)
    @NotNull(message = "codes cannot be null")
    //@Size(message = "code size must be  between {min} and {max}", min = 1, max = 4)
    private Integer codes;

    //@Column(nullable = false)
    @NotBlank(message = "gender cannot be null or empty")
    //@Size(message = "message size must be  between {min} and {max}", min = 3, max = 10)
    private String gender;

    //@Column(nullable = false)
    @NotBlank(message = "nationality cannot be null or empty")
    private String nationality;

    //@Column(nullable = false)
    @NotNull(message = "yearOfBirth cannot be null")
    private Integer yearOfBirth;

    List<CastsDto> casts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
