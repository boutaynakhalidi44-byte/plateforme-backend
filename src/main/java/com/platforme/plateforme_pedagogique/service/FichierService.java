package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FichierService {

    @Autowired
    private FichierRepository fichierRepository;

    @Autowired
    private ChapitreRepository chapitreRepository;

    private final String UPLOAD_DIR = "uploads/";

    public Fichier uploadFichier(
            MultipartFile file,
            Long chapitreId,
            String typeFichier,
            String titre) throws IOException {

        Chapitre chapitre = chapitreRepository
                .findById(chapitreId)
                .orElseThrow(() -> new RuntimeException("Chapitre non trouvé"));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);
        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        Fichier fichier = new Fichier();
        fichier.setNom(file.getOriginalFilename());
        fichier.setTitre(titre != null && !titre.isBlank() ? titre : file.getOriginalFilename());
        fichier.setType(file.getContentType());
        fichier.setUrl(UPLOAD_DIR + fileName);
        fichier.setDateUpload(new Date());
        fichier.setChapitre(chapitre);
        fichier.setTypeFichier(
                Fichier.TypeFichier.valueOf(typeFichier != null ? typeFichier : "COURS"));

        return fichierRepository.save(fichier);
    }

    public List<Fichier> getFichiersParChapitre(Long chapitreId) {
        return fichierRepository.findByChapitreId(chapitreId);
    }

    public List<Fichier> getFichiersParType(Long chapitreId, Fichier.TypeFichier type) {
        return fichierRepository.findByChapitreIdAndTypeFichier(chapitreId, type);
    }

    public void supprimerFichier(Long id) throws IOException {
        Fichier fichier = fichierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fichier non trouvé"));
        Files.deleteIfExists(Paths.get(fichier.getUrl()));
        fichierRepository.deleteById(id);
    }
}