package com.platforme.plateforme_pedagogique.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "fichiers")
public class Fichier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String titre;

    private String type;

    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpload;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;

    @Enumerated(EnumType.STRING)
    private TypeFichier typeFichier;

    public enum TypeFichier {
        COURS, TD
    }
}