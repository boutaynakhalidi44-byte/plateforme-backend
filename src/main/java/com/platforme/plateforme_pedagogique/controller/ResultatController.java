package com.platforme.plateforme_pedagogique.controller;


import com.platforme.plateforme_pedagogique.entity.Resultat;
import com.platforme.plateforme_pedagogique.service.ResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resultats")
@CrossOrigin(origins = "*")
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @PostMapping("/{quizId}/etudiant/{etudiantId}")
    public ResponseEntity<Resultat> soumettreQuiz(
            @PathVariable Long quizId,
            @PathVariable Long etudiantId,
            @RequestBody Map<Long, Long> reponses) {
        return ResponseEntity.ok(
                resultatService.soumettreQuiz(
                        quizId, etudiantId, reponses));
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Resultat>> getResultatsParEtudiant(
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
                resultatService.getResultatsParEtudiant(etudiantId));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Resultat>> getResultatsParQuiz(
            @PathVariable Long quizId) {
        return ResponseEntity.ok(
                resultatService.getResultatsParQuiz(quizId));
    }
}