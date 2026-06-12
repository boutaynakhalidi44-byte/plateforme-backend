package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.NotificationDTO;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    /**
     * ✅ Envoyer une notification à tous les étudiants inscrits (acceptés)
     * Si moduleId est fourni dans le DTO, cibler uniquement ce module.
     * Sinon, envoyer à tous les étudiants inscrits à n'importe quel module de l'enseignant.
     */
    public void envoyerNotification(NotificationDTO dto, Long enseignantId) {

        Enseignant enseignant = enseignantRepository
                .findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        List<Etudiant> destinataires;

        if (dto.getModuleId() != null) {
            // ✅ Cibler uniquement les étudiants d'un module spécifique
            destinataires = inscriptionRepository
                    .findByModuleId(dto.getModuleId())
                    .stream()
                    .filter(i -> i.getStatut() == Inscription.Statut.ACCEPTE)
                    .map(Inscription::getEtudiant)
                    .filter(e -> e != null)
                    .collect(Collectors.toList());
        } else {
            // Envoyer à tous les étudiants de la plateforme
            destinataires = etudiantRepository.findAll();
        }

        for (Etudiant etudiant : destinataires) {
            Notification notification = new Notification();
            notification.setTitre(dto.getTitre());
            notification.setContenu(dto.getContenu());
            notification.setDateEnvoi(new Date());
            notification.setEstLue(false);
            notification.setEnseignant(enseignant);
            notification.setEtudiant(etudiant);
            notification.setEnseignant(null);
            notificationRepository.save(notification);
        }
    }

    // ✅ Notifications reçues par un étudiant
    public List<Notification> getNotificationsParEtudiant(Long etudiantId) {
        return notificationRepository.findByEtudiantId(etudiantId);
    }

    // ✅ Notifications non lues d'un étudiant
    public List<Notification> getNotificationsNonLues(Long etudiantId) {
        return notificationRepository.findByEtudiantIdAndEstLueFalse(etudiantId);
    }

    // ✅ Marquer une notification comme lue
    public Notification marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        notification.setEstLue(true);
        return notificationRepository.save(notification);
    }

    // ✅ NOUVEAU : Notifications envoyées par l'enseignant (commentaires reçus)
    public List<Notification> getNotificationsParEnseignant(Long enseignantId) {
        return notificationRepository.findByEnseignantIdOrderByDateEnvoiDesc(enseignantId);
    }

    // ✅ NOUVEAU : Notifications non lues reçues par l'enseignant
    public List<Notification> getNotificationsNonLuesEnseignant(Long enseignantId) {
        return notificationRepository.findByEnseignantIdAndEstLueFalseOrderByDateEnvoiDesc(enseignantId);
    }

    // ✅ NOUVEAU : Compter notifications non lues (pour badge dans la navbar)
    public long countNonLues(Long etudiantId) {
        return notificationRepository.findByEtudiantIdAndEstLueFalse(etudiantId).size();
    }
}