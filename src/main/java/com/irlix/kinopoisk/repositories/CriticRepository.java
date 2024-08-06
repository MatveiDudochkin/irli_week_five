package com.irlix.kinopoisk.repositories;

import com.irlix.kinopoisk.entities.Critic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {
}
