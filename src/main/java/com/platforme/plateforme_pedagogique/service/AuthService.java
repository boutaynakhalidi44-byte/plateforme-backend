package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.*;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import com.platforme.plateforme_pedagogique.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    // ✅ Register — étudiant seulement
    public String register(RegisterRequest request) {

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        if ("ENSEIGNANT".equalsIgnoreCase(request.getRole())) {
            throw new RuntimeException(
                    "La création d'un compte enseignant n'est pas autorisée.");
        }

        Etudiant etudiant = new Etudiant();
        etudiant.setNom(request.getNom());
        etudiant.setPrenom(request.getPrenom());
        etudiant.setEmail(request.getEmail());
        etudiant.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        etudiant.setRole(Utilisateur.Role.ETUDIANT);
        etudiant.setNiveau(request.getNiveau());
        etudiant.setDateInscription(new Date());
        etudiantRepository.save(etudiant);

        return "Inscription réussie";
    }

    // ✅ Login
    public AuthResponse login(LoginRequest request) {

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email incorrect"));

        if (!passwordEncoder.matches(
                request.getMotDePasse(),
                utilisateur.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(
                utilisateur.getEmail(),
                utilisateur.getRole().name()
        );

        return new AuthResponse(
                token,
                utilisateur.getRole().name(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getId()
        );
    }

    // ✅ Mot de passe oublié
    public void demanderResetPassword(String email) {

        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email requis");
        }

        Optional<Utilisateur> optUtilisateur =
                utilisateurRepository.findByEmail(email.trim());

        if (optUtilisateur.isEmpty()) {
            throw new RuntimeException("Aucun compte trouvé avec cet email.");
        }

        String resetToken = UUID.randomUUID().toString();
        String resetUrl = "http://localhost:4200/reset-password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("boutayna.khalidi.bm@gmail.com"); // ← remplace par ton Gmail Brevo
        message.setTo(email.trim());
        message.setSubject("Réinitialisation de votre mot de passe — Platef.orm");
        message.setText(
                "Bonjour " + optUtilisateur.get().getPrenom() + ",\n\n" +
                        "Vous avez demandé la réinitialisation de votre mot de passe.\n\n" +
                        "Cliquez sur ce lien pour choisir un nouveau mot de passe :\n" +
                        resetUrl + "\n\n" +
                        "Ce lien est valable 30 minutes.\n\n" +
                        "Si vous n'êtes pas à l'origine de cette demande, ignorez cet email.\n\n" +
                        "L'équipe Platef.orm"
        );
        mailSender.send(message);
    }

    // ✅ Mettre à jour profil
    public void mettreAJourProfil(Long id, Map<String, String> data) {

        Utilisateur utilisateur = utilisateurRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (data.containsKey("nom") && !data.get("nom").isEmpty()) {
            utilisateur.setNom(data.get("nom"));
        }
        if (data.containsKey("prenom") && !data.get("prenom").isEmpty()) {
            utilisateur.setPrenom(data.get("prenom"));
        }
        if (data.containsKey("email") && !data.get("email").isEmpty()) {
            String nouvelEmail = data.get("email");
            if (!nouvelEmail.equals(utilisateur.getEmail()) &&
                    utilisateurRepository.existsByEmail(nouvelEmail)) {
                throw new RuntimeException("Cet email est déjà utilisé");
            }
            utilisateur.setEmail(nouvelEmail);
        }
        if (utilisateur instanceof Enseignant && data.containsKey("specialite")) {
            ((Enseignant) utilisateur).setSpecialite(data.get("specialite"));
        }

        utilisateurRepository.save(utilisateur);
    }

    // ✅ Changer mot de passe
    public void changerMotDePasse(Long id, Map<String, String> data) {

        Utilisateur utilisateur = utilisateurRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String ancienMotDePasse = data.get("ancienMotDePasse");
        String nouveauMotDePasse = data.get("nouveauMotDePasse");

        if (!passwordEncoder.matches(ancienMotDePasse, utilisateur.getMotDePasse())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }
        if (nouveauMotDePasse == null || nouveauMotDePasse.length() < 6) {
            throw new RuntimeException(
                    "Le nouveau mot de passe doit contenir au moins 6 caractères");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
        utilisateurRepository.save(utilisateur);
    }
}