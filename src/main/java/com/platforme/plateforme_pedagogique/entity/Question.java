package com.platforme.plateforme_pedagogique.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String enonce;

    @Enumerated(EnumType.STRING)
    private TypeQuestion type;

    private Integer points;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Reponse> reponses;

    public enum TypeQuestion {
        QCM, VRAI_FAUX
    }
}