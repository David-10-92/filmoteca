package com.example.filmoteca.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class FilmInput {
    private String image;
    private String title;
    private int date;
}
