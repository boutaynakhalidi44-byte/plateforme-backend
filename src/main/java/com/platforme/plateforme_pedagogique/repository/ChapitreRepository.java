package com.platforme.plateforme_pedagogique.repository;



import com.platforme.plateforme_pedagogique.entity.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChapitreRepository
        extends JpaRepository<Chapitre, Long> {

    List<Chapitre> findByModuleIdOrderByOrdre(Long moduleId);
}