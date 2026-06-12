package com.platforme.plateforme_pedagogique.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String titre;
    private String contenu;

    // ✅ NOUVEAU : optionnel — pour cibler les étudiants d'un module précis
    private Long moduleId;
}