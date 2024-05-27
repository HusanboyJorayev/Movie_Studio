package com.example.movie_studio.filter;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReadActorFromExcelFile {
    private Integer ID;
    private String NAME;
    private Integer CODES;
    private String GENDER;
    private String NATIONALITY;
    private Integer YEAR_OF_BIRTH;
    private String CREATED_AT;
}
