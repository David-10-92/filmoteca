package com.example.filmoteca.service.impl;

import com.example.filmoteca.dtos.FilmInput;
import com.example.filmoteca.dtos.FilmDetails;
import com.example.filmoteca.models.Film;
import com.example.filmoteca.repository.FilmRepository;
import com.example.filmoteca.service.FilmService;
import com.example.filmoteca.service.errors.ErrorCode;
import com.example.filmoteca.service.errors.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl  implements FilmService {

    @Autowired
    FilmRepository filmRepository;

    @Override
    public FilmDetails getFilm(int id) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if(optionalFilm.isPresent()){
            Film existFilm = optionalFilm.get();
            FilmDetails filmDetails = createFilmDetails(existFilm);
            return filmDetails;
        }else{
            throw new ServiceError(ErrorCode.RESOURCE_NOT_FOUND, "No se encontró la pelicula con el ID proporcionado");
        }
    }

    public FilmDetails createFilmDetails(Film film){
        FilmDetails filmDetails = new FilmDetails();
        filmDetails.setImage(film.getImage());
        filmDetails.setTitle(film.getTitle());
        filmDetails.setDate(film.getDate());
        filmDetails.setId(film.getId());
        return filmDetails;
    }

    @Override
    public Page<FilmDetails> listFilms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        List<FilmDetails> filmDetailsList = filmPage.getContent().stream()
                .map(film -> createFilmDetails(film))
                .collect(Collectors.toList());
        return new PageImpl<>(filmDetailsList, pageable, filmPage.getTotalElements());
    }

    @Override
    public String addFilm(FilmInput filmInput) {
        if (filmInput.getTitle() == null || filmInput.getTitle().isEmpty() ||
                filmInput.getImage() == null || filmInput.getImage().isEmpty() ||
                filmInput.getDate() <= 0) {
            return "Los parámetros no pueden estar vacíos o ser nulos";
        }
        Film film = new Film();
        film.setImage(filmInput.getImage());
        film.setTitle(filmInput.getTitle());
        film.setDate(filmInput.getDate());
        filmRepository.save(film);
        return "Pelicula creada correctamente";
    }

    @Override
    public String updateFilm(int id, FilmInput filmInput) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if(optionalFilm.isPresent()){
            Film existFilm = optionalFilm.get();
            if(filmInput.getTitle() != null && !filmInput.getTitle().isEmpty()){
                existFilm.setTitle(filmInput.getTitle());
            }
            if(filmInput.getImage() != null && !filmInput.getImage().isEmpty()){
                existFilm.setImage(filmInput.getImage());
            }
            if(filmInput.getDate() > 0){
                existFilm.setDate(filmInput.getDate());
            }
            filmRepository.save(existFilm);
            return "Pelicula modificada correctamente";
        }else {
            throw new ServiceError(ErrorCode.RESOURCE_NOT_FOUND, "No se encontró la pelicula con el ID proporcionado");
        }
    }

    @Override
    public String deleteFilm(int id) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if(optionalFilm.isPresent()){
            Film existFilm = optionalFilm.get();
            filmRepository.delete(existFilm);
            return "Pelicula eliminada correctamente";
        }else {
            throw new ServiceError(ErrorCode.RESOURCE_NOT_FOUND, "No se encontró la pelicula con el ID proporcionado");
        }
    }
}
