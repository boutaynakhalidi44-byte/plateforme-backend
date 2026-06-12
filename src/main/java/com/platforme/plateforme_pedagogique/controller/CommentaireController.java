package com.platforme.plateforme_pedagogique.controller;


import com.platforme.plateforme_pedagogique.dto.CommentaireDTO;
import com.platforme.plateforme_pedagogique.entity.Commentaire;
import com.platforme.plateforme_pedagogique.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@CrossOrigin(origins = "*")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    @PostMapping("/etudiant/{etudiantId}")
    public ResponseEntity<Commentaire> ajouterCommentaire(
            @RequestBody CommentaireDTO dto,
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
                commentaireService.ajouterCommentaire(
                        dto, etudiantId));
    }

    @PutMapping("/{id}/repondre")
    public ResponseEntity<Commentaire> repondreCommentaire(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> body) {
        return ResponseEntity.ok(
                commentaireService.repondreCommentaire(id, body.get("reponse")));
    }

    @GetMapping("/chapitre/{chapitreId}")
    public ResponseEntity<List<Commentaire>>
    getCommentairesParChapitre(
            @PathVariable Long chapitreId) {
        return ResponseEntity.ok(
                commentaireService
                        .getCommentairesParChapitre(chapitreId));
    }
}