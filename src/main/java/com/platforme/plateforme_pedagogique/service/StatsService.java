package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.dto.StatsEnseignantDTO;
import com.platforme.plateforme_pedagogique.dto.StatsEtudiantDTO;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired private InscriptionRepository  inscriptionRepository;
    @Autowired private ModuleRepository       moduleRepository;
    @Autowired private ChapitreRepository     chapitreRepository;
    @Autowired private QuizRepository         quizRepository;
    @Autowired private ResultatRepository     resultatRepository;

    public StatsEnseignantDTO getStatsEnseignant(Long enseignantId) {

        StatsEnseignantDTO stats = new StatsEnseignantDTO();
        List<Module> modules = moduleRepository.findByEnseignantId(enseignantId);
        stats.setTotalModules((long) modules.size());

        Set<Long> etudiantIds = new HashSet<>();
        long totalEnAttente = 0;

        for (Module module : modules) {
            List<Inscription> inscriptions = inscriptionRepository.findByModuleId(module.getId());
            for (Inscription i : inscriptions) {
                if (i.getStatut() == Inscription.Statut.ACCEPTE && i.getEtudiant() != null)
                    etudiantIds.add(i.getEtudiant().getId());
                if (i.getStatut() == Inscription.Statut.EN_ATTENTE)
                    totalEnAttente++;
            }
        }
        stats.setTotalEtudiants((long) etudiantIds.size());
        stats.setInscriptionsEnAttente(totalEnAttente);

        List<StatsEnseignantDTO.ModuleStatsDTO> statsModules = new ArrayList<>();
        for (Module module : modules) {
            StatsEnseignantDTO.ModuleStatsDTO ms = new StatsEnseignantDTO.ModuleStatsDTO();
            ms.setModuleId(module.getId());
            ms.setModuleTitre(module.getTitre());

            long nbInscrits = inscriptionRepository.findByModuleId(module.getId())
                    .stream().filter(i -> i.getStatut() == Inscription.Statut.ACCEPTE).count();
            ms.setNbInscrits(nbInscrits);

            long nbChapitres = chapitreRepository.findByModuleIdOrderByOrdre(module.getId()).size();
            ms.setNbChapitres(nbChapitres);

            double avg = getScoreMoyenModule(module);
            ms.setMoyenneScore(avg > 0 ? Math.round(avg * 10.0) / 10.0 : null);

            statsModules.add(ms);
        }
        stats.setStatsParModule(statsModules);

        List<Resultat> tousResultats = getTousResultats(modules);
        long total = tousResultats.size();
        stats.setTotalTentatives(total);

        if (total > 0) {
            double moy = tousResultats.stream()
                    .mapToDouble(r -> r.getScore() != null ? r.getScore() : 0)
                    .average().orElse(0);
            stats.setScoreMoyenGlobal(Math.round(moy * 10.0) / 10.0);

            long reussis = tousResultats.stream()
                    .filter(r -> r.getScore() != null && r.getScore() >= 70).count();
            stats.setTauxReussite(Math.round((reussis * 100.0 / total) * 10.0) / 10.0);
        } else {
            stats.setScoreMoyenGlobal(0.0);
            stats.setTauxReussite(0.0);
        }

        List<StatsEnseignantDTO.QuizStatsDTO> statsQuiz = new ArrayList<>();
        for (Module module : modules) {
            List<Resultat> resultatsModule = getResultatsModule(module);
            if (resultatsModule.isEmpty()) continue;

            StatsEnseignantDTO.QuizStatsDTO qs = new StatsEnseignantDTO.QuizStatsDTO();
            qs.setModuleId(module.getId());
            qs.setModuleTitre(module.getTitre());
            qs.setNbTentatives((long) resultatsModule.size());

            double moy = resultatsModule.stream()
                    .mapToDouble(r -> r.getScore() != null ? r.getScore() : 0)
                    .average().orElse(0);
            qs.setScoreMoyen(Math.round(moy * 10.0) / 10.0);

            long reussis = resultatsModule.stream()
                    .filter(r -> r.getScore() != null && r.getScore() >= 70).count();
            qs.setTauxReussite(Math.round((reussis * 100.0 / resultatsModule.size()) * 10.0) / 10.0);

            qs.setDistribution(calculerDistribution(resultatsModule));
            statsQuiz.add(qs);
        }
        stats.setStatsQuizParModule(statsQuiz);

        return stats;
    }

    public StatsEtudiantDTO getStatsEtudiant(Long etudiantId) {

        StatsEtudiantDTO stats = new StatsEtudiantDTO();

        List<Inscription> inscriptions = inscriptionRepository
                .findByEtudiantId(etudiantId).stream()
                .filter(i -> i.getStatut() == Inscription.Statut.ACCEPTE)
                .collect(Collectors.toList());

        stats.setTotalModules((long) inscriptions.size());

        List<StatsEtudiantDTO.ModuleProgressionDTO> statsModules = new ArrayList<>();
        for (Inscription inscription : inscriptions) {
            Module module = inscription.getModule();
            if (module == null) continue;

            StatsEtudiantDTO.ModuleProgressionDTO mp = new StatsEtudiantDTO.ModuleProgressionDTO();
            mp.setModuleId(module.getId());
            mp.setModuleTitre(module.getTitre());

            long totalChapitres = chapitreRepository
                    .findByModuleIdOrderByOrdre(module.getId()).size();
            mp.setTotalChapitres(totalChapitres);

            statsModules.add(mp);
        }
        stats.setStatsParModule(statsModules);
        return stats;
    }

    private List<Resultat> getTousResultats(List<Module> modules) {
        List<Resultat> tous = new ArrayList<>();
        for (Module module : modules)
            tous.addAll(getResultatsModule(module));
        return tous;
    }

    private List<Resultat> getResultatsModule(Module module) {
        List<Resultat> resultats = new ArrayList<>();
        List<Chapitre> chapitres = chapitreRepository.findByModuleIdOrderByOrdre(module.getId());
        for (Chapitre chapitre : chapitres) {
            quizRepository.findByChapitreId(chapitre.getId())
                    .ifPresent(quiz -> resultats.addAll(
                            resultatRepository.findByQuizId(quiz.getId())
                    ));
        }
        return resultats;
    }

    private double getScoreMoyenModule(Module module) {
        List<Resultat> r = getResultatsModule(module);
        if (r.isEmpty()) return 0;
        return r.stream()
                .mapToDouble(x -> x.getScore() != null ? x.getScore() : 0)
                .average().orElse(0);
    }

    private List<Long> calculerDistribution(List<Resultat> resultats) {
        long[] dist = new long[6];
        for (Resultat r : resultats) {
            if (r.getScore() == null) continue;
            double s = r.getScore() / 5.0;
            if      (s < 10) dist[0]++;
            else if (s < 12) dist[1]++;
            else if (s < 14) dist[2]++;
            else if (s < 16) dist[3]++;
            else if (s < 18) dist[4]++;
            else             dist[5]++;
        }
        List<Long> list = new ArrayList<>();
        for (long v : dist) list.add(v);
        return list;
    }
}