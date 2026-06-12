package com.platforme.plateforme_pedagogique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(name = "commentaire_id")
    private Long commentaireId;

    private String contenu;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoi;

    private Boolean estLue = false;

    // ✅ On cache enseignant et etudiant pour éviter les boucles infinies
    // Les IDs sont filtrés via les endpoints dédiés (/etudiant/{id}, /enseignant/{id})
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    @JsonIgnore
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnore
    private Etudiant etudiant;
}