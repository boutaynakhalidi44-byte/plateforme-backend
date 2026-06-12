package com.platforme.plateforme_pedagogique.dto;



import lombok.Data;
import java.util.List;

@Data
public class StatsEtudiantDTO {
    private Long totalModules;
    private Double moyenneScore;
    private Long totalQuizPasses;
    private List<ModuleProgressionDTO> statsParModule;

    @Data
    public static class ModuleProgressionDTO {
        private Long moduleId;
        private String moduleTitre;
        private Long totalChapitres;
    }
}