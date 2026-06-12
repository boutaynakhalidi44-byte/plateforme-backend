package com.platforme.plateforme_pedagogique.service;


import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class InscriptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public Inscription demanderInscription(
            Long etudiantId, Long moduleId) {

        if (inscriptionRepository
                .existsByEtudiantIdAndModuleId(
                        etudiantId, moduleId)) {
            throw new RuntimeException("Déjà inscrit");
        }

        Etudiant etudiant = etudiantRepository
                .findById(etudiantId)
                .orElseThrow(() ->
                        new RuntimeException("Etudiant non trouvé"));

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() ->
                        new RuntimeException("Module non trouvé"));

        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setModule(module);
        inscription.setDateInscription(new Date());
        inscription.setStatut(Inscription.Statut.EN_ATTENTE);

        return inscriptionRepository.save(inscription);
    }

    public Inscription accepterInscription(Long inscriptionId) {
        Inscription inscription = inscriptionRepository
                .findById(inscriptionId)
                .orElseThrow(() ->
                        new RuntimeException("Inscription non trouvée"));
        inscription.setStatut(Inscription.Statut.ACCEPTE);
        return inscriptionRepository.save(inscription);
    }

    public Inscription refuserInscription(Long inscriptionId) {
        Inscription inscription = inscriptionRepository
                .findById(inscriptionId)
                .orElseThrow(() ->
                        new RuntimeException("Inscription non trouvée"));
        inscription.setStatut(Inscription.Statut.REFUSE);
        return inscriptionRepository.save(inscription);
    }

    public List<Inscription> getInscriptionsParModule(
            Long moduleId) {
        return inscriptionRepository.findByModuleId(moduleId);
    }

    public List<Inscription> getInscriptionsParEtudiant(
            Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }
}
