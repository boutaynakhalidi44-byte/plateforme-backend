package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {

    // ✅ Toutes les réponses d'une question
    List<Reponse> findByQuestionId(Long questionId);
}