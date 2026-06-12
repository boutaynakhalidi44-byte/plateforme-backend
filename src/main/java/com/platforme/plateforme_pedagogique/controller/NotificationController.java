package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.dto.NotificationDTO;
import com.platforme.plateforme_pedagogique.entity.Notification;
import com.platforme.plateforme_pedagogique.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ Envoyer une notification aux étudiants
    @PostMapping("/enseignant/{enseignantId}")
    public ResponseEntity<String> envoyerNotification(
            @RequestBody NotificationDTO dto,
            @PathVariable Long enseignantId) {
        notificationService.envoyerNotification(dto, enseignantId);
        return ResponseEntity.ok("Notifications envoyées");
    }

    // ✅ Notifications reçues par un étudiant (toutes)
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Notification>> getNotificationsParEtudiant(
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
                notificationService.getNotificationsParEtudiant(etudiantId));
    }

    // ✅ Notifications non lues d'un étudiant
    @GetMapping("/etudiant/{etudiantId}/nonlues")
    public ResponseEntity<List<Notification>> getNotificationsNonLues(
            @PathVariable Long etudiantId) {
        return ResponseEntity.ok(
                notificationService.getNotificationsNonLues(etudiantId));
    }

    // ✅ Marquer une notification comme lue
    @PutMapping("/{id}/lire")
    public ResponseEntity<Notification> marquerCommeLue(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                notificationService.marquerCommeLue(id));
    }

    // ✅ NOUVEAU : Notifications reçues par l'enseignant (commentaires étudiants)
    @GetMapping("/enseignant/{enseignantId}/recues")
    public ResponseEntity<List<Notification>> getNotificationsEnseignant(
            @PathVariable Long enseignantId) {
        return ResponseEntity.ok(
                notificationService.getNotificationsParEnseignant(enseignantId));
    }

    // ✅ NOUVEAU : Notifications non lues de l'enseignant
    @GetMapping("/enseignant/{enseignantId}/nonlues")
    public ResponseEntity<List<Notification>> getNotificationsNonLuesEnseignant(
            @PathVariable Long enseignantId) {
        return ResponseEntity.ok(
                notificationService.getNotificationsNonLuesEnseignant(enseignantId));
    }

    // ✅ NOUVEAU : Compter les non lues (pour badge)
    @GetMapping("/etudiant/{etudiantId}/count")
    public ResponseEntity<Map<String, Long>> countNonLues(
            @PathVariable Long etudiantId) {
        long count = notificationService.countNonLues(etudiantId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}