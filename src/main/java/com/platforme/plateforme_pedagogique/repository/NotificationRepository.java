package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // ✅ Notifications d'un étudiant (toutes)
    List<Notification> findByEtudiantId(Long etudiantId);

    // ✅ Notifications non lues d'un étudiant
    List<Notification> findByEtudiantIdAndEstLueFalse(Long etudiantId);

    // ✅ NOUVEAU : Notifications reçues par l'enseignant (commentaires étudiants)
    List<Notification> findByEnseignantIdOrderByDateEnvoiDesc(Long enseignantId);

    // ✅ NOUVEAU : Notifications non lues reçues par l'enseignant
    List<Notification> findByEnseignantIdAndEstLueFalseOrderByDateEnvoiDesc(Long enseignantId);
}