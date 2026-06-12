package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.dto.StatsEnseignantDTO;
import com.platforme.plateforme_pedagogique.dto.StatsEtudiantDTO;
import com.platforme.plateforme_pedagogique.repository.*;
import com.platforme.plateforme_pedagogique.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ChapitreRepository chapitreRepository;

    // ✅ Stats globales enseignant
    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<StatsEnseignantDTO> getStatsEnseignant(
            @PathVariable Long enseignantId) {
        return ResponseEntity.ok(
                statsService.getStatsEnseignant(enseignantId));
    }

    // ✅ Stats globales étudiant
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<StatsEtudiantDTO> getStatsEtudiant(
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
                statsService.getStatsEtudiant(etudiantId));
    }
    }