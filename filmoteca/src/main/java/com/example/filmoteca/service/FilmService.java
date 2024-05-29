package com.example.filmoteca.service;

import com.example.filmoteca.dtos.FilmDetails;
import com.example.filmoteca.dtos.FilmInput;
import com.example.filmoteca.models.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmService {
    FilmDetails getFilm(int id);
    Page<FilmDetails> listFilms(int page, int size);
    String addFilm(FilmInput filmInput);
    String updateFilm(int id, FilmInput filmInput);
    String deleteFilm(int id);
}
