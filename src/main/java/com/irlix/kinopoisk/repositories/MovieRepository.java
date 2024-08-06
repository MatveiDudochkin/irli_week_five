package com.irlix.kinopoisk.repositories;

import com.irlix.kinopoisk.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenresIn(Set<Genre> genres);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByRating(Double rating);

    List<Movie> findByYear(Integer year);
}
