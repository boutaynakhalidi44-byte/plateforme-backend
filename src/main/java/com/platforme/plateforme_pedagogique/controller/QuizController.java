package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.dto.QuizDTO;
import com.platforme.plateforme_pedagogique.entity.Quiz;
import com.platforme.plateforme_pedagogique.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/{chapitreId}")
    public ResponseEntity<Quiz> creerQuiz(
            @RequestBody QuizDTO dto,
            @PathVariable Long chapitreId) {
        return ResponseEntity.ok(
                quizService.creerQuiz(dto, chapitreId));
    }

    @GetMapping("/chapitre/{chapitreId}")
    public ResponseEntity<Quiz> getQuizParChapitre(
            @PathVariable Long chapitreId) {
        try {
            Quiz quiz = quizService.getQuizParChapitre(chapitreId);
            return ResponseEntity.ok(quiz);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizParId(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(quizService.getQuizParId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> modifierQuiz(
            @PathVariable Long id,
            @RequestBody QuizDTO dto) {
        try {
            return ResponseEntity.ok(quizService.modifierQuiz(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerQuiz(
            @PathVariable Long id) {
        quizService.supprimerQuiz(id);
        return ResponseEntity.ok("Quiz supprimé");
    }
}