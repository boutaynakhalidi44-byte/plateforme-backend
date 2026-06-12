package com.platforme.plateforme_pedagogique.controller;


import com.platforme.plateforme_pedagogique.dto.ReponseDTO;
import com.platforme.plateforme_pedagogique.entity.Reponse;
import com.platforme.plateforme_pedagogique.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reponses")
@CrossOrigin(origins = "*")
public class ReponseController {

    @Autowired
    private ReponseService reponseService;

    @PostMapping("/{questionId}")
    public ResponseEntity<Reponse> ajouterReponse(
            @RequestBody ReponseDTO dto,
            @PathVariable Long questionId) {
        return ResponseEntity.ok(
                reponseService.ajouterReponse(dto, questionId));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Reponse>> getReponsesParQuestion(
            @PathVariable Long questionId) {
        return ResponseEntity.ok(
                reponseService.getReponsesParQuestion(questionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reponse> modifierReponse(
            @PathVariable Long id,
            @RequestBody ReponseDTO dto) {
        return ResponseEntity.ok(
                reponseService.modifierReponse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerReponse(
            @PathVariable Long id) {
        reponseService.supprimerReponse(id);
        return ResponseEntity.ok("Réponse supprimée");
    }
}