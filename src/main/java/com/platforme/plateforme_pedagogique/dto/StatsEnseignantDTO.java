package com.platforme.plateforme_pedagogique.dto;

import lombok.Data;
import java.util.List;

@Data
public class StatsEnseignantDTO {

    // ── Existant ──────────────────────────────────────
    private Long totalModules;
    private Long totalEtudiants;
    private Long inscriptionsEnAttente;
    private List<ModuleStatsDTO> statsParModule;

    // ── Nouveau : stats quiz globales ─────────────────
    private Long totalTentatives;
    private Double scoreMoyenGlobal;
    private Double tauxReussite;

    // ── Nouveau : stats quiz par module ───────────────
    private List<QuizStatsDTO> statsQuizParModule;

    @Data
    public static class ModuleStatsDTO {
        private Long moduleId;
        private String moduleTitre;
        private Long nbInscrits;
        private Long nbChapitres;
        private Double moyenneScore;
    }

    @Data
    public static class QuizStatsDTO {
        private Long moduleId;
        private String moduleTitre;
        private Long nbTentatives;
        private Double scoreMoyen;
        private Double tauxReussite;
        private List<Long> distribution;
    }
}