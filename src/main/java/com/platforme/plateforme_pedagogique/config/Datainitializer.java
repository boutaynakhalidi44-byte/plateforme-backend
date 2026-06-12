package com.platforme.plateforme_pedagogique.config;

import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Date;



@Component
public class Datainitializer implements CommandLineRunner {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // ✅ Crée le compte enseignant uniquement s'il n'existe pas
        if (enseignantRepository.count() == 0) {
            Enseignant enseignant = new Enseignant();
            enseignant.setNom("Admin");
            enseignant.setPrenom("Enseignant");
            enseignant.setEmail("admin@plateforme.com");
            enseignant.setMotDePasse(passwordEncoder.encode("admin123"));
            enseignant.setRole(Utilisateur.Role.ENSEIGNANT);
            enseignant.setSpecialite("Informatique");
            enseignant.setDateInscription(new Date());
            enseignantRepository.save(enseignant);

            System.out.println("========================================");
            System.out.println("✅ Compte enseignant créé automatiquement");
            System.out.println("   Email    : admin@plateforme.com");
            System.out.println("   Password : admin123");
            System.out.println("   ⚠️  Changez le mot de passe en production !");
            System.out.println("========================================");
        }
    }
}