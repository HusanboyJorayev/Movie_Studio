package com.example.movie_studio.studio;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class StudioDto {
    private Integer id;
    @NotBlank(message = "companyName cannot be null or empty")
    private String companyName;
    @NotBlank(message = "city cannot be null or empty")
    private String city;
    @NotNull(message = "founded cannot be null")
    private Integer founded;
    @NotBlank(message = "companyType cannot be null or empty")
    private String companyType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
