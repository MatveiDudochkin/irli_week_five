package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.MovieDTO;
import com.irlix.kinopoisk.entities.Critic;
import com.irlix.kinopoisk.entities.Genre;
import com.irlix.kinopoisk.entities.Movie;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.CriticRepository;
import com.irlix.kinopoisk.repositories.GenreRepository;
import com.irlix.kinopoisk.repositories.MovieRepository;
import com.irlix.kinopoisk.repositories.ReviewRepository;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MapperConfig mapperConfig;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;
    private final CriticRepository criticRepository;

    public MovieService(MovieRepository movieRepository, MapperConfig mapperConfig, GenreRepository genreRepository, ReviewRepository reviewRepository, CriticRepository criticRepository) {
        this.movieRepository = movieRepository;
        this.mapperConfig = mapperConfig;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;
        this.criticRepository = criticRepository;
    }

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream().map(mapperConfig::mapToMovieDTO).collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        return mapperConfig.mapToMovieDTO(movieRepository.findById(id).orElseThrow(() -> new CustomException("movie not found ")));
    }

    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = mapperConfig.mapToMovie(movieDTO);
        movie.setGenres(movieDTO.getGenres().stream()
                .map(genreDto -> {
                    if (genreDto.getId() != null) {
                        return genreRepository.findById(genreDto.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Genre not found"));
                    } else {
                        Genre genre = mapperConfig.mapToGenre(genreDto);
                        return genreRepository.save(genre); // Сохраняем новый жанр
                    }
                }).collect(Collectors.toSet()));

        // Обработка отзывов
        movie.setReviews(movieDTO.getReviews().stream()
                .map(reviewDto -> {
                    if (reviewDto.getId() != null) {
                        return reviewRepository.findById(reviewDto.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
                    } else {
                        Critic critic = null;
                        if (reviewDto.getCriticId() != null) {
                            critic = criticRepository.findById(reviewDto.getCriticId())
                                    .orElseThrow(() -> new IllegalArgumentException("Critic not found"));
                        }
                        return mapperConfig.mapToReview(reviewDto, movie, critic);
                    }
                }).collect(Collectors.toSet()));

        Movie savedMovie = movieRepository.save(movie);
        return mapperConfig.mapToMovieDTO(savedMovie);
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