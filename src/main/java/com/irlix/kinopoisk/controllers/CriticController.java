package com.irlix.kinopoisk.controllers;

import com.irlix.kinopoisk.dto.CriticDTO;
import com.irlix.kinopoisk.services.CriticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CriticDTO>> getAllCritics() {
        List<CriticDTO> critics = criticService.getAllCritic();
        return ResponseEntity.ok(critics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriticDTO> getCriticById(@PathVariable Long id) {
        CriticDTO critic = criticService.getCriticById(id);
        return ResponseEntity.ok(critic);
    }

    @PostMapping("/new")
    public ResponseEntity<CriticDTO> createCritic(@RequestBody CriticDTO criticDTO) {
        CriticDTO createdCritic = criticService.createCritic(criticDTO);
        return ResponseEntity.ok(createdCritic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriticDTO> updateCritic(@PathVariable Long id, @RequestBody CriticDTO criticDTO) {
        CriticDTO updatedCritic = criticService.updateCritic(id, criticDTO);
        return ResponseEntity.ok(updatedCritic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCritic(@PathVariable Long id) {
        criticService.deleteCritic(id);
        return ResponseEntity.noContent().build();
    }
}
