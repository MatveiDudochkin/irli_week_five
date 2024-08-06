package com.irlix.kinopoisk.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private int duration;
    private double rating;
    private int year;
    private Set<GenreDTO> genres;
    private Set<ReviewDTO> reviews;
}
