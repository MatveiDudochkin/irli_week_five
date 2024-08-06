package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.GenreDTO;
import com.irlix.kinopoisk.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDTO> getAllGenre() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDTO getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id);
    }

    @PostMapping("/new")
    public GenreDTO createGenre(@RequestBody GenreDTO genreDTO) {
        return genreService.createGenre(genreDTO);
    }

    @PutMapping("/{id}")
    public GenreDTO updateGenre(@PathVariable Long id, @RequestBody GenreDTO genreDTO) {
        return genreService.updateGenre(id, genreDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCritic(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }
}
