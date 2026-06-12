package com.platforme.plateforme_pedagogique.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private Integer duree;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "chapitre_id")
    @JsonIgnore
    private Chapitre chapitre;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Resultat> resultats;
}
