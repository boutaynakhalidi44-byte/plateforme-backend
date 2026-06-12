package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.CommentaireDTO;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ChapitreRepository chapitreRepository;

    // ✅ AJOUTÉ : pour créer la notification au prof
    @Autowired
    private NotificationRepository notificationRepository;

    public Commentaire ajouterCommentaire(CommentaireDTO dto, Long etudiantId) {

        Etudiant etudiant = etudiantRepository
                .findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        Chapitre chapitre = chapitreRepository
                .findById(dto.getChapitreId())
                .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));

        // Sauvegarder le commentaire
        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(dto.getContenu());
        commentaire.setDateCommentaire(new Date());
        commentaire.setEtudiant(etudiant);
        commentaire.setChapitre(chapitre);

        Commentaire saved = commentaireRepository.save(commentaire);

        // ✅ CORRECTION : Créer une notification pour l'enseignant du module
        try {
            Module module = chapitre.getModule();
            if (module != null && module.getEnseignant() != null) {
                Enseignant enseignant = module.getEnseignant();

                Notification notif = new Notification();
                notif.setTitre("💬 Nouveau commentaire");
                notif.setContenu(etudiant.getPrenom() + " " + etudiant.getNom() +
                        " a posté un commentaire sur le chapitre \"" +
                        chapitre.getTitre() + "\" : " +
                        dto.getContenu());
                notif.setDateEnvoi(new Date());
                notif.setEstLue(false);
                notif.setEnseignant(enseignant);
                notif.setEtudiant(etudiant);
                notif.setCommentaireId(saved.getId());notif.setEtudiant(null);
                notificationRepository.save(notif);
            }
        } catch (Exception e) {
            // Ne pas bloquer l'enregistrement du commentaire si la notif échoue
            System.err.println("⚠️ Erreur création notification commentaire: " + e.getMessage());
        }

        return saved;
    }

    public Commentaire repondreCommentaire(Long commentaireId, String reponse) {

        Commentaire commentaire = commentaireRepository
                .findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        commentaire.setReponseEnseignant(reponse);
        Commentaire saved = commentaireRepository.save(commentaire);

        // ✅ Notifier l'étudiant que sa question a reçu une réponse
        try {
            Etudiant etudiant = commentaire.getEtudiant();
            Chapitre chapitre = commentaire.getChapitre();
            Module module = chapitre.getModule();

            if (etudiant != null && module != null) {
                Notification notif = new Notification();
                notif.setTitre("📩 Réponse à votre commentaire");
                notif.setContenu(
                        "L'enseignant a répondu à votre commentaire sur \"" +
                                chapitre.getTitre() + "\" : " + reponse
                );
                notif.setDateEnvoi(new Date());
                notif.setEstLue(false);
                notif.setEnseignant(module.getEnseignant());
                notif.setEtudiant(etudiant);notif.setEnseignant(null);
                notificationRepository.save(notif);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Erreur notification réponse: " + e.getMessage());
        }

        return saved;
    }

    public List<Commentaire> getCommentairesParChapitre(Long chapitreId) {
        return commentaireRepository.findByChapitreId(chapitreId);
    }
}