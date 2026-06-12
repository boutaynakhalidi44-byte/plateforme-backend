package com.platforme.plateforme_pedagogique.dto;


import lombok.Data;

@Data
public class CommentaireDTO {
    private String contenu;
    private Long chapitreId;
}