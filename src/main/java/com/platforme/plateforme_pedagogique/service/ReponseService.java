package com.platforme.plateforme_pedagogique.service;



import com.platforme.plateforme_pedagogique.dto.ReponseDTO;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReponseService {

    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // زيد إجابة لسؤال موجود
    public Reponse ajouterReponse(
            ReponseDTO dto, Long questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException("Question non trouvée"));

        Reponse reponse = new Reponse();
        reponse.setContenu(dto.getContenu());
        reponse.setEstCorrecte(dto.getEstCorrecte());
        reponse.setQuestion(question);

        return reponseRepository.save(reponse);
    }

    // عدل إجابة
    public Reponse modifierReponse(Long id, ReponseDTO dto) {
        Reponse reponse = reponseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Réponse non trouvée"));

        reponse.setContenu(dto.getContenu());
        reponse.setEstCorrecte(dto.getEstCorrecte());

        return reponseRepository.save(reponse);
    }

    // حذف إجابة
    public void supprimerReponse(Long id) {
        reponseRepository.deleteById(id);
    }

    // جلب إجابات سؤال
    public List<Reponse> getReponsesParQuestion(Long questionId) {
        return reponseRepository.findByQuestionId(questionId);
    }
}