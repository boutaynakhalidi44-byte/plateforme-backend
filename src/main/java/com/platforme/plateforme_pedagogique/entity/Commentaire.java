package com.platforme.plateforme_pedagogique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "commentaires")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenu;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommentaire;

    private String reponseEnseignant;

    // ✅ CORRECTION : exposer nom/prenom de l'étudiant pour l'affichage
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnoreProperties({"motDePasse", "inscriptions", "commentaires"})
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "chapitre_id")
    @JsonIgnore
    private Chapitre chapitre;
}