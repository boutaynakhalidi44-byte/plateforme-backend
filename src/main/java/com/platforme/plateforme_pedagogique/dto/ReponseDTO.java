package com.platforme.plateforme_pedagogique.dto;


import lombok.Data;

@Data
public class ReponseDTO {
    private String contenu;
    private Boolean estCorrecte;
}