package com.platforme.plateforme_pedagogique.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomName;       // ex: "module-42-abc123"

    @Column(nullable = false)
    private String lienJitsi;      // ex: "https://meet.jit.si/module-42-abc123"

    private Boolean actif = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;
}
