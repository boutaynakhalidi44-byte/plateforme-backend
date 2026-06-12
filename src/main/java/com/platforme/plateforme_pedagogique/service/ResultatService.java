package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ResultatService {

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Resultat soumettreQuiz(
            Long quizId,
            Long etudiantId,
            Map<Long, Long> reponses) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));

        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

        double pointsObtenus = 0;
        double totalPoints = 0;

        for (Map.Entry<Long, Long> entry : reponses.entrySet()) {
            Long questionId = entry.getKey();
            Long reponseId = entry.getValue();

            Question question = questionRepository.findById(questionId)
                    .orElse(null);
            if (question == null) continue;

            totalPoints += question.getPoints();

            Reponse reponse = reponseRepository.findById(reponseId)
                    .orElse(null);
            if (reponse != null && reponse.getEstCorrecte()) {
                pointsObtenus += question.getPoints();
            }
        }

        // ✅ CORRECTION : score sur 100 (pas sur 20) — cohérent avec le frontend
        double scoreFinal = totalPoints > 0
                ? (pointsObtenus / totalPoints) * 100.0
                : 0.0;

        // Calcul numéro de tentative
        List<Resultat> anciensResultats = resultatRepository
                .findByEtudiantIdAndQuizId(etudiantId, quizId);

        Resultat resultat = new Resultat();
        resultat.setScore(scoreFinal);
        resultat.setDatePassage(new Date());
        resultat.setTentative(anciensResultats.size() + 1);
        resultat.setEtudiant(etudiant);
        resultat.setQuiz(quiz);

        return resultatRepository.save(resultat);
    }

    public List<Resultat> getResultatsParEtudiant(Long etudiantId) {
        return resultatRepository.findByEtudiantId(etudiantId);
    }

    public List<Resultat> getResultatsParQuiz(Long quizId) {
        return resultatRepository.findByQuizId(quizId);
    }
}