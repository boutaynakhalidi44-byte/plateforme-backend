package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.*;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReponseRepository reponseRepository;

    // ✅ Créer un quiz complet avec questions et réponses
    @Transactional
    public Quiz creerQuiz(QuizDTO dto, Long chapitreId) {
        Chapitre chapitre = chapitreRepository.findById(chapitreId)
                .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));

        Quiz quiz = new Quiz();
        quiz.setTitre(dto.getTitre());
        quiz.setDuree(dto.getDuree());
        quiz.setDateCreation(new Date());
        quiz.setChapitre(chapitre);
        Quiz savedQuiz = quizRepository.save(quiz);

        ajouterQuestions(savedQuiz, dto.getQuestions());

        return quizRepository.findById(savedQuiz.getId()).orElse(savedQuiz);
    }

    // ✅ CORRECTION : modifierQuiz supprime et recrée les questions
    @Transactional
    public Quiz modifierQuiz(Long id, QuizDTO dto) {
        Quiz quiz = getQuizParId(id);
        quiz.setTitre(dto.getTitre());
        quiz.setDuree(dto.getDuree());

        // Supprimer les anciennes questions (cascade supprime les réponses)
        List<Question> anciennesQuestions = questionRepository.findByQuizId(id);
        for (Question q : anciennesQuestions) {
            reponseRepository.deleteAll(reponseRepository.findByQuestionId(q.getId()));
        }
        questionRepository.deleteAll(anciennesQuestions);

        quizRepository.save(quiz);

        // Recréer les nouvelles questions
        if (dto.getQuestions() != null) {
            ajouterQuestions(quiz, dto.getQuestions());
        }

        return quizRepository.findById(id).orElse(quiz);
    }

    private void ajouterQuestions(Quiz quiz, List<QuestionDTO> questionDTOs) {
        if (questionDTOs == null) return;
        for (QuestionDTO questionDTO : questionDTOs) {
            Question question = new Question();
            question.setEnonce(questionDTO.getEnonce());
            question.setType(Question.TypeQuestion.valueOf(questionDTO.getType()));
            question.setPoints(questionDTO.getPoints());
            question.setQuiz(quiz);
            Question savedQuestion = questionRepository.save(question);

            if (questionDTO.getReponses() != null) {
                for (ReponseDTO reponseDTO : questionDTO.getReponses()) {
                    Reponse reponse = new Reponse();
                    reponse.setContenu(reponseDTO.getContenu());
                    reponse.setEstCorrecte(reponseDTO.getEstCorrecte());
                    reponse.setQuestion(savedQuestion);
                    reponseRepository.save(reponse);
                }
            }
        }
    }

    public Quiz getQuizParChapitre(Long chapitreId) {
        return quizRepository.findByChapitreId(chapitreId)
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));
    }

    public Quiz getQuizParId(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz non trouvé"));
    }

    @Transactional
    public void supprimerQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}