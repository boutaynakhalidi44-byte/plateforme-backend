package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.repository.*;
import com.platforme.plateforme_pedagogique.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChapitreService {

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public Chapitre ajouterChapitre(ChapitreDTO dto, Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() ->
                        new RuntimeException("Module non trouvé"));

        Chapitre chapitre = new Chapitre();
        chapitre.setTitre(dto.getTitre());
        chapitre.setDescription(dto.getDescription());
        chapitre.setOrdre(dto.getOrdre());
        chapitre.setModule(module);

        return chapitreRepository.save(chapitre);
    }

    public List<Chapitre> getTousLesChapitres() {
        return chapitreRepository.findAll();
    }

    public List<Chapitre> getChapitresParModule(Long moduleId) {
        return chapitreRepository
                .findByModuleIdOrderByOrdre(moduleId);
    }

    public Chapitre modifierChapitre(Long id, ChapitreDTO dto) {
        Chapitre chapitre = chapitreRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Chapitre non trouvé"));
        chapitre.setTitre(dto.getTitre());
        chapitre.setDescription(dto.getDescription());
        chapitre.setOrdre(dto.getOrdre());
        return chapitreRepository.save(chapitre);
    }

    public void supprimerChapitre(Long id) {
        chapitreRepository.deleteById(id);
    }
}