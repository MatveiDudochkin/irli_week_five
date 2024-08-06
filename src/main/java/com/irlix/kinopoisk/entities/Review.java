package com.irlix.kinopoisk.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rating")
    private double rating;
    @Column(name = "message")
    private String message;
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    
    @ManyToOne
    @JoinColumn(name = "critic_id")
    private Critic critic;
}
