package com.platforme.plateforme_pedagogique.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "inscriptions")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInscription;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    // ✅ Exposer etudiant avec infos utiles (nom, prenom, email, niveau)
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnoreProperties({"motDePasse", "inscriptions", "commentaires"})
    private Etudiant etudiant;

    // ✅ Exposer module avec id et titre seulement
    @ManyToOne
    @JoinColumn(name = "module_id")
    @JsonIgnoreProperties({"inscriptions", "chapitres", "enseignant"})
    private Module module;

    public enum Statut {
        EN_ATTENTE, ACCEPTE, REFUSE
    }
}