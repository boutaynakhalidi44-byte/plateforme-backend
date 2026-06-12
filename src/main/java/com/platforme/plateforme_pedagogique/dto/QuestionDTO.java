package com.platforme.plateforme_pedagogique.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private String enonce;
    private String type;
    private Integer points;
    private List<ReponseDTO> reponses;
}