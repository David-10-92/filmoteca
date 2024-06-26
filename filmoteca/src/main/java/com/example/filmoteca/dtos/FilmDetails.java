package com.example.filmoteca.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FilmDetails {
    private Integer id;
    private String image;
    private String title;
    private int date;
}
