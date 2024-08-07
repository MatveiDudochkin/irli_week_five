package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.ReviewDTO;
import com.irlix.kinopoisk.entities.*;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.*;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MapperConfig mapperConfig;
    private final CriticRepository criticRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, MapperConfig mapperConfig, CriticRepository criticRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.mapperConfig = mapperConfig;
        this.criticRepository = criticRepository;
        this.movieRepository = movieRepository;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(mapperConfig::mapToReviewDTO).collect(Collectors.toList());
    }


    public Review getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElseThrow(() -> new RuntimeException("Review not found"));
    }


    public Review createReview(ReviewDTO reviewDTO) {
        Movie movie = movieRepository.findById(reviewDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
        Critic critic = criticRepository.findById(reviewDTO.getCriticId()).orElseThrow(() -> new RuntimeException("Critic not found"));
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setMessage(reviewDTO.getMessage());
        review.setDate(reviewDTO.getDate());
        review.setMovie(movie);
        review.setCritic(critic);
        review = reviewRepository.save(review);

        movie.getReviews().add(review);
        movie.updateRating();

        movieRepository.save(movie);

        return review;
    }

    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new CustomException("review not found "));
        review.setRating(reviewDTO.getRating());
        review.setMessage(reviewDTO.getMessage());
        review.setDate(reviewDTO.getDate());
        reviewRepository.save(review);
        return mapperConfig.mapToReviewDTO(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewDTO> getAllReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream().map(mapperConfig::mapToReviewDTO).collect(Collectors.toList());
    }
}
