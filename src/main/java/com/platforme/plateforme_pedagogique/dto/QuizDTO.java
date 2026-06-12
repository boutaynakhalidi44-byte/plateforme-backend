package com.platforme.plateforme_pedagogique.dto;


import lombok.Data;
import java.util.List;

@Data
public class QuizDTO {
    private String titre;
    private Integer duree;
    private List<QuestionDTO> questions;
}