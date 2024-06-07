package com.example.filmoteca.controller;

import com.example.filmoteca.dtos.FilmInput;
import com.example.filmoteca.dtos.FilmDetails;
import com.example.filmoteca.models.Film;
import com.example.filmoteca.repository.FilmRepository;
import com.example.filmoteca.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/films")
public class FilmController {

    @Autowired
    FilmService filmService;

    @Autowired
    FilmRepository filmRepository;

    @GetMapping("/addFilm")
    public String addFilmPage(Model model) {
        model.addAttribute("filmInput", new FilmInput());
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String addFilm(@ModelAttribute("filmInput") FilmInput filmInput, Model model) {
        filmService.addFilm(filmInput);
        model.addAttribute("message", "Película agregada exitosamente");
        return "redirect:/films";
    }

    @GetMapping("/details/{id}")
    public String filmDetails(@PathVariable("id") Integer id, Model model) {
        FilmDetails film = filmService.getFilm(id);
        model.addAttribute("film", film);
        return "filmDetails";
    }

    @GetMapping()
    public String listFilms(Model model,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<FilmDetails> films = filmService.listFilms(page,size);
        model.addAttribute("films", films);
        model.addAttribute("currentPage", page);
        return "films";
    }

    @GetMapping("/updateFilm/{id}")
    public String viewUpdatePage(@PathVariable("id") Integer id, Model model) {
        FilmDetails film = filmService.getFilm(id);
        model.addAttribute("filmInput", film);
        return "updateFilm";
    }

    @RequestMapping("/updateFilm")
    public String updateFilm(@RequestParam("id") Integer id, @ModelAttribute("filmInput") FilmInput filmInput) {
        filmService.updateFilm(id,filmInput);
        return "redirect:/films";
    }

    @RequestMapping("/deleteFilm")
    public String deleteFilm(@RequestParam("id") Integer id){
        filmService.deleteFilm(id);
        return "redirect:/films";
    }
}
