package com.example.movie_studio.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorFilter {
    private Long id;
    private String name;
    private Integer codes;
    private String gender;
    private String nationality;
    private Integer yearOfBirth;
}
