package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.MovieDTO;
import com.irlix.kinopoisk.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Увидеть всех критиков, либо сортировать по названию, году, оценке, здесь же пагинация")
    @GetMapping
    public Page<MovieDTO> getMovies(
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return movieService.getAllMovies(sortBy, sortOrder, page, size);
    }

    @Operation(summary = "Определенное кино по id")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        MovieDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Создать новое кино")
    @PostMapping("/new")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO createdMovie = movieService.createMovie(movieDTO);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @Operation(summary = "Изменить поля фильма")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovie = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @Operation(summary = "Удалить фильм")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Все фильмы по определенному жанру")
    @GetMapping("/by-genres")
    public List<MovieDTO> getMoviesByGenreNames(@RequestParam List<String> genreNames) {
        return movieService.getMoviesByGenreNames(genreNames);
    }

    @Operation(summary = "Поиск по названию")
    @GetMapping("/search/title")
    public List<MovieDTO> searchMoviesByTitle(@RequestParam String title) {
        return movieService.findMoviesByTitle(title);
    }

    @Operation(summary = "Поиск по рейтингу")
    @GetMapping("/search/rating")
    public List<MovieDTO> searchMoviesByRating(@RequestParam Double rating) {
        return movieService.findMoviesByRating(rating);
    }

    @Operation(summary = "Поиск по году")
    @GetMapping("/search/year")
    public List<MovieDTO> searchMoviesByYear(@RequestParam Integer year) {
        return movieService.findMoviesByYear(year);
    }

    @Operation(summary = "Фильтрация по промежутку дат")
    @GetMapping("/search/yearRange")
    public List<MovieDTO> searchMoviesByYearRange(@RequestParam Integer startYear, @RequestParam Integer endYear) {
        return movieService.findMoviesByYearRange(startYear, endYear);
    }

    @Operation(summary = "Фильтрация по оценкам (от-до)")
    @GetMapping("/search/ratingRange")
    public List<MovieDTO> searchMoviesByRatingRange(@RequestParam Double minRating, @RequestParam Double maxRating) {
        return movieService.findMoviesByRatingRange(minRating, maxRating);
    }
}