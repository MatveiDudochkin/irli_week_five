package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.MovieDTO;
import com.irlix.kinopoisk.entities.Movie;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.MovieRepository;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MapperConfig mapperConfig;

    public MovieService(MovieRepository movieRepository, MapperConfig mapperConfig) {
        this.movieRepository = movieRepository;
        this.mapperConfig = mapperConfig;
    }

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(mapperConfig::mapToMovieDTO).collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        return mapperConfig.mapToMovieDTO(movieRepository.findById(id).orElseThrow(() -> new CustomException("movie not found ")));
    }

    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = mapperConfig.mapToMovie(movieDTO);
        movieRepository.save(movie);
        return mapperConfig.mapToMovieDTO(movie);
    }

    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new CustomException("movie not found"));
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDuration(movieDTO.getDuration());
        movie.setRating(movieDTO.getRating());
        movie.setYear(movieDTO.getYear());
        movieRepository.save(movie);
        return mapperConfig.mapToMovieDTO(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
