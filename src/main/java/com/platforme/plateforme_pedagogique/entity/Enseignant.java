package com.platforme.plateforme_pedagogique.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "enseignants")
public class Enseignant extends Utilisateur {

    private String specialite;
}