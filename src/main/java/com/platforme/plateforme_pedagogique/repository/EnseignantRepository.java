package com.platforme.plateforme_pedagogique.repository;


import com.platforme.plateforme_pedagogique.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository
        extends JpaRepository<Enseignant, Long> {
}