package com.platforme.plateforme_pedagogique.repository;



import com.platforme.plateforme_pedagogique.entity.Inscription;
import com.platforme.plateforme_pedagogique.entity.Inscription.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InscriptionRepository
        extends JpaRepository<Inscription, Long> {

    List<Inscription> findByModuleId(Long moduleId);
    List<Inscription> findByEtudiantId(Long etudiantId);
    List<Inscription> findByStatut(Statut statut);
    Optional<Inscription> findByEtudiantIdAndModuleId(
            Long etudiantId, Long moduleId);
    Boolean existsByEtudiantIdAndModuleId(
            Long etudiantId, Long moduleId);
}