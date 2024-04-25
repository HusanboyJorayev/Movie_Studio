package com.example.movie_studio.actor;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SomeActorFields {
    private String name;
    private Integer codes;
    private String gender;
    private String nationality;
    private Integer yearOfBirth;
}
