package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.MovieDTO;
import com.irlix.kinopoisk.entities.*;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.*;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public Page<MovieDTO> getAllMovies(@NonNull String sortBy, @NonNull String sortOrder, int page, int size) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        sort = sortOrder.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies.map(mapperConfig::mapToMovieDTO);
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

    public List<MovieDTO> getMoviesByGenreNames(List<String> genreNames) {
        List<Genre> genres = genreRepository.findByNameIn(genreNames);
        Set<Genre> genreSet = new HashSet<>(genres);
        List<Movie> movies = movieRepository.findByGenresIn(genreSet);
        return movies.stream().map(mapperConfig::mapToMovieDTO).collect(Collectors.toList());
    }

    public List<MovieDTO> findMoviesByTitle(String title) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
        return movies.stream().map(mapperConfig::mapToMovieDTO).collect(Collectors.toList());
    }

    public List<MovieDTO> findMoviesByRating(Double rating) {
        List<Movie> movies = movieRepository.findByRating(rating);
        return movies.stream().map(mapperConfig::mapToMovieDTO).collect(Collectors.toList());
    }

    public List<MovieDTO> findMoviesByYear(Integer year) {
        List<Movie> movies = movieRepository.findByYear(year);
        return movies.stream().map(mapperConfig::mapToMovieDTO).collect(Collectors.toList());
    }
}