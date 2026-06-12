package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.entity.Fichier;
import com.platforme.plateforme_pedagogique.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/fichiers")
@CrossOrigin(origins = "*")
public class FichierController {

    @Autowired
    private FichierService fichierService;

    @PostMapping("/{chapitreId}")
    public ResponseEntity<Fichier> uploadFichier(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long chapitreId,
            @RequestParam(value = "typeFichier", defaultValue = "COURS") String typeFichier,
            @RequestParam(value = "titre", required = false) String titre)
            throws IOException {
        return ResponseEntity.ok(
                fichierService.uploadFichier(file, chapitreId, typeFichier, titre));
    }

    @GetMapping("/chapitre/{chapitreId}")
    public ResponseEntity<List<Fichier>> getFichiersParChapitre(@PathVariable Long chapitreId) {
        return ResponseEntity.ok(fichierService.getFichiersParChapitre(chapitreId));
    }

    @GetMapping("/chapitre/{chapitreId}/cours")
    public ResponseEntity<List<Fichier>> getCoursParChapitre(@PathVariable Long chapitreId) {
        return ResponseEntity.ok(fichierService.getFichiersParType(chapitreId, Fichier.TypeFichier.COURS));
    }

    @GetMapping("/chapitre/{chapitreId}/td")
    public ResponseEntity<List<Fichier>> getTDsParChapitre(@PathVariable Long chapitreId) {
        return ResponseEntity.ok(fichierService.getFichiersParType(chapitreId, Fichier.TypeFichier.TD));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerFichier(@PathVariable Long id) throws IOException {
        fichierService.supprimerFichier(id);
        return ResponseEntity.ok("Fichier supprimé");
    }
}