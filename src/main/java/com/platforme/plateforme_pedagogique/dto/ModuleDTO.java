package com.platforme.plateforme_pedagogique.dto;

import lombok.Data;

@Data
public class ModuleDTO {
    private Long id;
    private String titre;
    private String description;
    private String categorie;
    private String image;
    private int nombreChapitres;
}