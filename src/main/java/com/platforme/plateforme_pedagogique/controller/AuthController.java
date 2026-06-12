package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.dto.*;
import com.platforme.plateforme_pedagogique.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // ✅ Mot de passe oublié
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody Map<String, String> body) {
        String email = body.get("email");
        authService.demanderResetPassword(email);
        return ResponseEntity.ok("Si cet email est enregistré, un lien a été envoyé.");
    }

    // ✅ Nouveau — mettre à jour le profil
    @PutMapping("/profil/{id}")
    public ResponseEntity<String> mettreAJourProfil(
            @PathVariable Long id,
            @RequestBody Map<String, String> data) {
        authService.mettreAJourProfil(id, data);
        return ResponseEntity.ok("Profil mis à jour");
    }

    // ✅ Nouveau — changer le mot de passe
    @PutMapping("/changer-mot-de-passe/{id}")
    public ResponseEntity<String> changerMotDePasse(
            @PathVariable Long id,
            @RequestBody Map<String, String> data) {
        authService.changerMotDePasse(id, data);
        return ResponseEntity.ok("Mot de passe changé");
    }
}