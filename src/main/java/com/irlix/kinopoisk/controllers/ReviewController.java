package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.ReviewDTO;
import com.irlix.kinopoisk.entities.Review;
import com.irlix.kinopoisk.services.ReviewService;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final MapperConfig mapperConfig;

    @Autowired
    public ReviewController(ReviewService reviewService, MapperConfig mapperConfig) {
        this.reviewService = reviewService;
        this.mapperConfig = mapperConfig;
    }

    @GetMapping
    public List<ReviewDTO> getAllReview() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewDTO getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return mapperConfig.mapToReviewDTO(review);
    }

    @PostMapping("/new")
    public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.createReview(reviewDTO);
        return mapperConfig.mapToReviewDTO(review);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(id, reviewDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
