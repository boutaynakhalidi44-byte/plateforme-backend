package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FichierRepository
        extends JpaRepository<Fichier, Long> {


    List<Fichier> findByChapitreId(Long chapitreId);

    List<Fichier> findByChapitreIdAndTypeFichier(
            Long chapitreId, Fichier.TypeFichier type);

}
