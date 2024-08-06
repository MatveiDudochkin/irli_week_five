package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.GenreDTO;
import com.irlix.kinopoisk.entities.Genre;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.GenreRepository;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final MapperConfig mapperConfig;

    public GenreService(GenreRepository genreRepository, MapperConfig mapperConfig) {
        this.genreRepository = genreRepository;
        this.mapperConfig = mapperConfig;
    }

    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream().map(mapperConfig::mapToGenreDTO).collect(Collectors.toList());
    }

    public GenreDTO getGenreById(Long id) {
        return mapperConfig.mapToGenreDTO(genreRepository.findById(id).orElseThrow(() -> new CustomException("genre not found ")));
    }

    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = mapperConfig.mapToGenre(genreDTO);
        genreRepository.save(genre);
        return mapperConfig.mapToGenreDTO(genre);
    }

    public GenreDTO updateGenre(Long id, GenreDTO genreDTO) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new CustomException("genre not found "));
        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());
        genreRepository.save(genre);
        return mapperConfig.mapToGenreDTO(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
