package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.*;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ReponseRepository reponseRepository;

    // زيد سؤال لـ Quiz موجود
    public Question ajouterQuestion(
            QuestionDTO dto, Long quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() ->
                        new RuntimeException("Quiz non trouvé"));

        Question question = new Question();
        question.setEnonce(dto.getEnonce());
        question.setType(Question.TypeQuestion
                .valueOf(dto.getType()));
        question.setPoints(dto.getPoints());
        question.setQuiz(quiz);
        Question saved = questionRepository.save(question);

        // زيد الإجابات معاه
        for (ReponseDTO reponseDTO : dto.getReponses()) {
            Reponse reponse = new Reponse();
            reponse.setContenu(reponseDTO.getContenu());
            reponse.setEstCorrecte(reponseDTO.getEstCorrecte());
            reponse.setQuestion(saved);
            reponseRepository.save(reponse);
        }

        return saved;
    }

    // عدل سؤال
    public Question modifierQuestion(
            Long id, QuestionDTO dto) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Question non trouvée"));

        question.setEnonce(dto.getEnonce());
        question.setType(Question.TypeQuestion
                .valueOf(dto.getType()));
        question.setPoints(dto.getPoints());

        return questionRepository.save(question);
    }

    // حذف سؤال
    public void supprimerQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    // جلب أسئلة Quiz
    public List<Question> getQuestionsParQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }
}