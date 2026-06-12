package com.platforme.plateforme_pedagogique.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private String description;

    private String categorie;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;


    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Chapitre> chapitres;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions;
}