package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.NotificationDTO;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeetingService {

    @Autowired private MeetingRepository meetingRepository;
    @Autowired private ModuleRepository moduleRepository;
    @Autowired private EnseignantRepository enseignantRepository;
    @Autowired private NotificationService notificationService;

    /**
     * Le prof lance un meeting pour un module.
     * - Génère un nom de room unique
     * - Sauvegarde en base
     * - Envoie une notification à tous les étudiants inscrits (acceptés) au module
     */
    public Meeting lancerMeeting(Long moduleId, Long enseignantId) {

        // Désactiver tout meeting actif existant pour ce module
        meetingRepository.findByModuleIdAndActifTrue(moduleId)
                .ifPresent(ancien -> {
                    ancien.setActif(false);
                    meetingRepository.save(ancien);
                });

        // Créer la room Jitsi
        String roomName = "module-" + moduleId + "-" + UUID.randomUUID().toString().substring(0, 6);
        String lien = "https://meet.jit.si/" + roomName;

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        Meeting meeting = new Meeting();
        meeting.setRoomName(roomName);
        meeting.setLienJitsi(lien);
        meeting.setActif(true);
        meeting.setDateCreation(new Date());
        meeting.setModule(module);
        meeting.setEnseignant(enseignant);
        meetingRepository.save(meeting);

        // Notifier les étudiants inscrits au module
        NotificationDTO dto = new NotificationDTO();
        dto.setTitre("📹 Cours en direct — " + module.getTitre());
        dto.setContenu("MEETING:" + lien); // préfixe MEETING: pour que le frontend détecte et affiche un bouton
        dto.setModuleId(moduleId);
        notificationService.envoyerNotification(dto, enseignantId);

        return meeting;
    }

    /**
     * Récupérer le meeting actif d'un module (étudiant ou prof)
     */
    public Optional<Meeting> getMeetingActif(Long moduleId) {
        return meetingRepository.findByModuleIdAndActifTrue(moduleId);
    }

    /**
     * Le prof termine le meeting
     */
    public void terminerMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting non trouvé"));
        meeting.setActif(false);
        meetingRepository.save(meeting);
    }
}
