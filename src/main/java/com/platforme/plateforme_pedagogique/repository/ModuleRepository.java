package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ModuleRepository
        extends JpaRepository<Module, Long> {

    List<Module> findByEnseignantId(Long enseignantId);
    List<Module> findByCategorie(String categorie);
}