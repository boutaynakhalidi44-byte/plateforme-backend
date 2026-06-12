package com.platforme.plateforme_pedagogique.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;
    private String specialite;
    private String niveau;
}