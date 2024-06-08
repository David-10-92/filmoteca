package com.example.filmoteca.service;

import com.example.filmoteca.dtos.FilmDetails;
import com.example.filmoteca.dtos.FilmInput;
import com.example.filmoteca.models.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmService {
    FilmDetails getFilm(int id);
    Page<FilmDetails> listFilms(int page, int size);
    Film addFilm(FilmInput filmInput);
    Film updateFilm(int id, FilmInput filmInput);
    Film deleteFilm(int id);
}
