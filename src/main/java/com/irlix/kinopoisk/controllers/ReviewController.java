package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.ReviewDTO;
import com.irlix.kinopoisk.entities.Review;
import com.irlix.kinopoisk.services.ReviewService;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO review = mapperConfig.mapToReviewDTO(reviewService.getReviewById(id));
        return ResponseEntity.ok(review);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(mapperConfig.mapToReviewDTO(createdReview));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
