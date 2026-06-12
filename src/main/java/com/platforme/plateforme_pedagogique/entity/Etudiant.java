package com.platforme.plateforme_pedagogique.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "etudiants")
public class Etudiant extends Utilisateur {

    private String niveau;
}
