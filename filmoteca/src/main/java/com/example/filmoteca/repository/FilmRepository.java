package com.example.filmoteca.repository;

import com.example.filmoteca.models.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    Film getFilmById(Integer id);
    Page<Film> findAll(Pageable pageable);
}
