package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // ✅ Toutes les questions d'un quiz
    List<Question> findByQuizId(Long quizId);
}