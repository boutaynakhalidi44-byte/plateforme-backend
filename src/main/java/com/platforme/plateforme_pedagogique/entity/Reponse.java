package com.platforme.plateforme_pedagogique.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reponses")
public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenu;

    private Boolean estCorrecte;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;
}