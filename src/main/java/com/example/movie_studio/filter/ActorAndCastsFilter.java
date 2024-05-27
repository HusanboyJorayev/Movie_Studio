package com.example.movie_studio.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorAndCastsFilter {
    private Long id;
    private String name;
    private Integer codes;
    private String gender;
    private String nationality;
    private Integer yearOfBirth;


    private Long actorId;
    private String roleType;

    private LocalDateTime actorCreatedAt;
    private LocalDateTime castsCreatedAt;
}
