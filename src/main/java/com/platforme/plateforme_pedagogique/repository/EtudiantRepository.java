package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EtudiantRepository
        extends JpaRepository<Etudiant, Long> {
}