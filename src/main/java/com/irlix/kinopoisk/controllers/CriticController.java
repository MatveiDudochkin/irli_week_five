package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.CriticDTO;
import com.irlix.kinopoisk.services.CriticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/critic")
public class CriticController {

    private final CriticService criticService;

    @Autowired
    public CriticController(CriticService criticService) {
        this.criticService = criticService;
    }

    @GetMapping
    public List<CriticDTO> getAllCritic() {
        return criticService.getAllCritic();
    }

    @GetMapping("/{id}")
    public CriticDTO getCriticById(@PathVariable Long id) {
        return criticService.getCriticById(id);
    }

    @PostMapping("/new")
    public CriticDTO createCritic(@RequestBody CriticDTO criticDTO) {
        return criticService.createCritic(criticDTO);
    }

    @PutMapping("/{id}")
    public CriticDTO updateCritic(@PathVariable Long id, @RequestBody CriticDTO criticDTO) {
        return criticService.updateCritic(id, criticDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCritic(@PathVariable Long id) {
        criticService.deleteCritic(id);
    }
}
