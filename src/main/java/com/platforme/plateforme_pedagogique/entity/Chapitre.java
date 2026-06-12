package com.platforme.plateforme_pedagogique.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "chapitres")
public class Chapitre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private String description;

    private Integer ordre;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(mappedBy = "chapitre", cascade = CascadeType.ALL)
    private List<Fichier> fichiers;

    @OneToMany(mappedBy = "chapitre", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;


    @OneToMany(mappedBy = "chapitre", cascade = CascadeType.ALL)
    private List<Commentaire> commentaires;
}