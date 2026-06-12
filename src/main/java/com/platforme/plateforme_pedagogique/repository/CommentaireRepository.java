package com.platforme.plateforme_pedagogique.repository;



import com.platforme.plateforme_pedagogique.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentaireRepository
        extends JpaRepository<Commentaire, Long> {

    List<Commentaire> findByChapitreId(Long chapitreId);
    List<Commentaire> findByEtudiantId(Long etudiantId);
}