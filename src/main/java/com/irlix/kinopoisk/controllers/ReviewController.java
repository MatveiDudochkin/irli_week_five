package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.ReviewDTO;
import com.irlix.kinopoisk.entities.Review;
import com.irlix.kinopoisk.services.ReviewService;
import com.irlix.kinopoisk.utils.MapperConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
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

    @Operation(summary = "Посмотреть все рецензии")
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "Посмотреть определенную рецензию по id")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO review = mapperConfig.mapToReviewDTO(reviewService.getReviewById(id));
        return ResponseEntity.ok(review);
    }

    @Operation(summary = "Создать новую рецензию")
    @PostMapping("/new")
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            Review review = reviewService.createReview(reviewDTO);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Изменить поля рецензии")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @Operation(summary = "Удалить рецензию")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Все рецензии по id фильма")
    @GetMapping("/movie/{movieId}")
    public List<ReviewDTO> getReviewsByMovieId(@PathVariable Long movieId) {
        return reviewService.getAllReviewsByMovieId(movieId);
    }
}
