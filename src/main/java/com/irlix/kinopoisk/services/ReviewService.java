package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.ReviewDTO;
import com.irlix.kinopoisk.entities.Review;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.ReviewRepository;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MapperConfig mapperConfig;

    public ReviewService(ReviewRepository reviewRepository, MapperConfig mapperConfig) {
        this.reviewRepository = reviewRepository;
        this.mapperConfig = mapperConfig;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(mapperConfig::mapToReviewDTO).collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Long id) {
        return mapperConfig.mapToReviewDTO(reviewRepository.findById(id).orElseThrow(() -> new CustomException("review not found ")));
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = mapperConfig.mapToReview(reviewDTO);
        reviewRepository.save(review);
        return mapperConfig.mapToReviewDTO(review);
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
}
