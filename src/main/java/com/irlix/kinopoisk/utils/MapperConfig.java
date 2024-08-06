package com.irlix.kinopoisk.utils;

import com.irlix.kinopoisk.dto.*;
import com.irlix.kinopoisk.entities.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperConfig {
    public GenreDTO mapToGenreDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());
        return dto;
    }

    public Genre mapToGenre(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());
        return genre;
    }

    public MovieDTO mapToMovieDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setDuration(movie.getDuration());
        dto.setRating(movie.getRating());
        dto.setYear(movie.getYear());
        dto.setGenres(movie.getGenres().stream().map(this::mapToGenreDTO).collect(Collectors.toSet()));
        dto.setReviews(movie.getReviews().stream().map(this::mapToReviewDTO).collect(Collectors.toSet()));
        return dto;
    }

    public Movie mapToMovie(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setDuration(dto.getDuration());
        movie.setRating(dto.getRating());
        movie.setYear(dto.getYear());
        movie.setGenres(dto.getGenres().stream().map(this::mapToGenre).collect(Collectors.toSet()));
        movie.setReviews(dto.getReviews().stream().map(reviewDTO -> mapToReview(reviewDTO, movie, null)).collect(Collectors.toSet()));
        return movie;
    }

    public CriticDTO mapToCriticDTO(Critic critic) {
        CriticDTO dto = new CriticDTO();
        dto.setId(critic.getId());
        dto.setFirst_name(critic.getFirst_name());
        dto.setLast_name(critic.getLast_name());
        dto.setInfo(critic.getInfo());
        return dto;
    }

    public Critic mapToCritic(CriticDTO dto) {
        Critic critic = new Critic();
        critic.setId(dto.getId());
        critic.setFirst_name(dto.getFirst_name());
        critic.setLast_name(dto.getLast_name());
        critic.setInfo(dto.getInfo());
        return critic;
    }

    public ReviewDTO mapToReviewDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setMessage(review.getMessage());
        dto.setDate(review.getDate());
        dto.setMovieId(review.getMovie().getId());
        dto.setCriticId(review.getCritic().getId());
        return dto;
    }

    public Review mapToReview(ReviewDTO dto, Movie movie, Critic critic) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setMessage(dto.getMessage());
        review.setDate(dto.getDate());
        review.setMovie(movie);
        review.setCritic(critic);
        return review;
    }
}
