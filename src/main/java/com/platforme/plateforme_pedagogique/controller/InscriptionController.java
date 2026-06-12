package com.platforme.plateforme_pedagogique.controller;


import com.platforme.plateforme_pedagogique.entity.Inscription;
import com.platforme.plateforme_pedagogique.service.InscriptionService;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @PostMapping("/etudiant/{etudiantId}/module/{moduleId}")
    public ResponseEntity<?> demanderInscription(
            @PathVariable Long etudiantId,
            @PathVariable Long moduleId) {
        try {
            return ResponseEntity.ok(
                    inscriptionService.demanderInscription(etudiantId, moduleId));
        } catch (RuntimeException e) {
            // Retourne 400 avec le message au lieu de 403
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/accepter")
    public ResponseEntity<Inscription> accepterInscription(@PathVariable Long id) {
        return ResponseEntity.ok(inscriptionService.accepterInscription(id));
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<Inscription> refuserInscription(@PathVariable Long id) {
        return ResponseEntity.ok(inscriptionService.refuserInscription(id));
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<Inscription>> getInscriptionsParModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsParModule(moduleId));
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Inscription>> getInscriptionsParEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsParEtudiant(etudiantId));
    }

    @Autowired
    private InscriptionRepository inscriptionRepository;  // ← ajoute ça en haut avec les autres @Autowired

    @PutMapping("/{id}/reinitialiser")
    public ResponseEntity<Void> reinitialiser(@PathVariable Long id) {
        inscriptionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
