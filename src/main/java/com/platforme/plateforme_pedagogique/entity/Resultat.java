package com.platforme.plateforme_pedagogique.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "resultats")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePassage;

    private Integer tentative;

    // ✅ CORRECTION : on expose etudiant avec seulement id/nom/prenom
    // (évite la boucle infinie mais garde les infos utiles)
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnoreProperties({"motDePasse", "inscriptions", "resultats", "commentaires"})
    private Etudiant etudiant;

    // ✅ CORRECTION : on expose quiz avec id et titre
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnoreProperties({"questions", "resultats", "chapitre"})
    private Quiz quiz;
}