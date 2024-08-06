package com.irlix.kinopoisk.services;

import com.irlix.kinopoisk.dto.CriticDTO;
import com.irlix.kinopoisk.entities.Critic;
import com.irlix.kinopoisk.exception.CustomException;
import com.irlix.kinopoisk.repositories.CriticRepository;
import com.irlix.kinopoisk.utils.MapperConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CriticService {
    private final CriticRepository criticRepository;
    private final MapperConfig mapperConfig;

    public CriticService(CriticRepository criticRepository, MapperConfig mapperConfig) {
        this.criticRepository = criticRepository;
        this.mapperConfig = mapperConfig;
    }

    public List<CriticDTO> getAllCritic() {
        return criticRepository.findAll().stream().map(mapperConfig::mapToCriticDTO).collect(Collectors.toList());
    }

    public CriticDTO getCriticById(Long id) {
        return mapperConfig.mapToCriticDTO(criticRepository.findById(id).orElseThrow(() -> new CustomException("critic not found ")));
    }

    public CriticDTO createCritic(CriticDTO criticDTO) {
        Critic critic = mapperConfig.mapToCritic(criticDTO);
        criticRepository.save(critic);
        return mapperConfig.mapToCriticDTO(critic);
    }

    public CriticDTO updateCritic(Long id, CriticDTO criticDTO) {
        Critic critic = criticRepository.findById(id).orElseThrow(() -> new CustomException("movie not found"));
        critic.setFirst_name(criticDTO.getFirst_name());
        critic.setLast_name(criticDTO.getLast_name());
        critic.setInfo(criticDTO.getInfo());
        criticRepository.save(critic);
        return mapperConfig.mapToCriticDTO(critic);
    }

    public void deleteCritic(Long id) {
        criticRepository.deleteById(id);
    }
}
