package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.entity.Meeting;
import com.platforme.plateforme_pedagogique.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired private MeetingService meetingService;

    /**
     * POST /api/meetings/lancer/{moduleId}/enseignant/{enseignantId}
     * Le prof lance un meeting pour son module
     */
    @PostMapping("/lancer/{moduleId}/enseignant/{enseignantId}")
    public ResponseEntity<Meeting> lancerMeeting(
            @PathVariable Long moduleId,
            @PathVariable Long enseignantId) {
        Meeting meeting = meetingService.lancerMeeting(moduleId, enseignantId);
        return ResponseEntity.ok(meeting);
    }

    /**
     * GET /api/meetings/module/{moduleId}/actif
     * Récupérer le meeting actif d'un module (étudiant ou prof)
     */
    @GetMapping("/module/{moduleId}/actif")
    public ResponseEntity<?> getMeetingActif(@PathVariable Long moduleId) {
        return meetingService.getMeetingActif(moduleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/meetings/{meetingId}/terminer
     * Le prof termine le meeting
     */
    @PutMapping("/{meetingId}/terminer")
    public ResponseEntity<Map<String, String>> terminerMeeting(@PathVariable Long meetingId) {
        meetingService.terminerMeeting(meetingId);
        return ResponseEntity.ok(Map.of("message", "Meeting terminé"));
    }
}
