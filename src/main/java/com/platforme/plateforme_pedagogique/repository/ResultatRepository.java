package com.platforme.plateforme_pedagogique.repository;



import com.platforme.plateforme_pedagogique.entity.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultatRepository
        extends JpaRepository<Resultat, Long> {

    List<Resultat> findByEtudiantId(Long etudiantId);
    List<Resultat> findByQuizId(Long quizId);
    List<Resultat> findByEtudiantIdAndQuizId(Long etudiantId, Long quizId);
}