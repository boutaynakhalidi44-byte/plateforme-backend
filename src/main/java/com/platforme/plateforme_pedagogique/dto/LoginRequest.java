package com.platforme.plateforme_pedagogique.dto;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String motDePasse;
}