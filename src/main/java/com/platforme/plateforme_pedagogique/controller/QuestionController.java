package com.platforme.plateforme_pedagogique.controller;



import com.platforme.plateforme_pedagogique.dto.QuestionDTO;
import com.platforme.plateforme_pedagogique.entity.Question;
import com.platforme.plateforme_pedagogique.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/{quizId}")
    public ResponseEntity<Question> ajouterQuestion(
            @RequestBody QuestionDTO dto,
            @PathVariable Long quizId) {
        return ResponseEntity.ok(
                questionService.ajouterQuestion(dto, quizId));
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionsParQuiz(
            @PathVariable Long quizId) {
        return ResponseEntity.ok(
                questionService.getQuestionsParQuiz(quizId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> modifierQuestion(
            @PathVariable Long id,
            @RequestBody QuestionDTO dto) {
        return ResponseEntity.ok(
                questionService.modifierQuestion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerQuestion(
            @PathVariable Long id) {
        questionService.supprimerQuestion(id);
        return ResponseEntity.ok("Question supprimée");
    }
}