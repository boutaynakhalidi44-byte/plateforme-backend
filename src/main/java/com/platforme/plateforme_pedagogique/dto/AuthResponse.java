package com.platforme.plateforme_pedagogique.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
    private String nom;
    private String prenom;
    private Long id;
}