package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.dto.ChapitreDTO;
import com.platforme.plateforme_pedagogique.entity.Chapitre;
import com.platforme.plateforme_pedagogique.service.ChapitreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chapitres")
@CrossOrigin(origins = "*")
public class ChapitreController {

    @Autowired
    private ChapitreService chapitreService;

    @PostMapping("/{moduleId}")
    public ResponseEntity<Chapitre> ajouterChapitre(
            @RequestBody ChapitreDTO dto,
            @PathVariable Long moduleId) {
        return ResponseEntity.ok(
                chapitreService.ajouterChapitre(dto, moduleId));
    }

    @GetMapping
    public ResponseEntity<List<Chapitre>> getTousLesChapitres() {
        return ResponseEntity.ok(
                chapitreService.getTousLesChapitres());
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<Chapitre>> getChapitresParModule(
            @PathVariable Long moduleId) {
        return ResponseEntity.ok(
                chapitreService.getChapitresParModule(moduleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chapitre> modifierChapitre(
            @PathVariable Long id,
            @RequestBody ChapitreDTO dto) {
        return ResponseEntity.ok(
                chapitreService.modifierChapitre(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerChapitre(
            @PathVariable Long id) {
        chapitreService.supprimerChapitre(id);
        return ResponseEntity.ok("Chapitre supprimé");
    }
}