package com.platforme.plateforme_pedagogique.service;


import com.platforme.plateforme_pedagogique.dto.ModuleDTO;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.entity.*;
import com.platforme.plateforme_pedagogique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;





    public List<ModuleDTO> getTousLesModulesDTO() {
        return moduleRepository.findAll().stream().map(m -> {
            ModuleDTO dto = new ModuleDTO();
            dto.setId(m.getId());
            dto.setTitre(m.getTitre());
            dto.setDescription(m.getDescription());
            dto.setCategorie(m.getCategorie());
            dto.setImage(m.getImage());
            dto.setNombreChapitres(
                    m.getChapitres() != null ? m.getChapitres().size() : 0
            );
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
    public ModuleDTO getModuleParIdDTO(Long id) {
        Module m = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));

        ModuleDTO dto = new ModuleDTO();
        dto.setId(m.getId());
        dto.setTitre(m.getTitre());
        dto.setDescription(m.getDescription());
        dto.setImage(m.getImage());
        dto.setCategorie(m.getCategorie());
        dto.setNombreChapitres(
                m.getChapitres() != null ? m.getChapitres().size() : 0
        );
        return dto;
    }

    // إضافة module
    public Module ajouterModule(ModuleDTO dto, Long enseignantId) {
        Enseignant enseignant = enseignantRepository
                .findById(enseignantId)
                .orElseThrow(() ->
                        new RuntimeException("Enseignant non trouvé"));

        Module module = new Module();
        module.setTitre(dto.getTitre());
        module.setDescription(dto.getDescription());
        module.setCategorie(dto.getCategorie());
        module.setImage(dto.getImage());
        module.setDateCreation(new Date());
        module.setEnseignant(enseignant);

        return moduleRepository.save(module);
    }

    // جلب كل modules
    public List<Module> getTousLesModules() {
        return moduleRepository.findAll();
    }

    // جلب modules تاع أستاذ
    public List<Module> getModulesParEnseignant(Long enseignantId) {
        return moduleRepository.findByEnseignantId(enseignantId);
    }

    // جلب module بالid
    public Module getModuleParId(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Module non trouvé"));
    }

    // تعديل module
    public Module modifierModule(Long id, ModuleDTO dto) {
        Module module = getModuleParId(id);
        module.setTitre(dto.getTitre());
        module.setDescription(dto.getDescription());
        module.setCategorie(dto.getCategorie());
        module.setImage(dto.getImage());
        return moduleRepository.save(module);
    }

    // حذف module
    public void supprimerModule(Long id) {
        moduleRepository.deleteById(id);
    }
}