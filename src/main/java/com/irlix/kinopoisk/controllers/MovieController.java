package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.MovieDTO;
import com.irlix.kinopoisk.entities.Movie;
import com.irlix.kinopoisk.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public Page<MovieDTO> getMovies(
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return movieService.getAllMovies(sortBy, sortOrder, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        MovieDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/new")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO createdMovie = movieService.createMovie(movieDTO);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovie = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-genres")
    public List<MovieDTO> getMoviesByGenreNames(@RequestParam List<String> genreNames) {
        return movieService.getMoviesByGenreNames(genreNames);
    }

    @GetMapping("/search/title")
    public List<MovieDTO> searchMoviesByTitle(@RequestParam String title) {
        return movieService.findMoviesByTitle(title);
    }

    @GetMapping("/search/rating")
    public List<MovieDTO> searchMoviesByRating(@RequestParam Double rating) {
        return movieService.findMoviesByRating(rating);
    }

    @GetMapping("/search/year")
    public List<MovieDTO> searchMoviesByYear(@RequestParam Integer year) {
        return movieService.findMoviesByYear(year);
    }

    @GetMapping("/search/yearRange")
    public List<MovieDTO> searchMoviesByYearRange(@RequestParam Integer startYear, @RequestParam Integer endYear) {
        return movieService.findMoviesByYearRange(startYear, endYear);
    }

    @GetMapping("/search/ratingRange")
    public List<MovieDTO> searchMoviesByRatingRange(@RequestParam Double minRating, @RequestParam Double maxRating) {
        return movieService.findMoviesByRatingRange(minRating, maxRating);
    }
}