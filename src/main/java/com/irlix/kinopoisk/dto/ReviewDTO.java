package com.irlix.kinopoisk.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private double rating;
    private String message;
    private LocalDate date;
    private Long movieId;
    private Long criticId;
}
