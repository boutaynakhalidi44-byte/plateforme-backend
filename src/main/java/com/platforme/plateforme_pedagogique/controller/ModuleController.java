package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.dto.ModuleDTO;
import com.platforme.plateforme_pedagogique.entity.Module;
import com.platforme.plateforme_pedagogique.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/{enseignantId}")
    public ResponseEntity<Module> ajouterModule(
            @RequestBody ModuleDTO dto,
            @PathVariable Long enseignantId) {
        return ResponseEntity.ok(
                moduleService.ajouterModule(dto, enseignantId));
    }

    @GetMapping
    public ResponseEntity<List<ModuleDTO>> getTousLesModules() {
        return ResponseEntity.ok(moduleService.getTousLesModulesDTO());
    }
    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Module>> getModulesParEnseignant(
            @PathVariable Long enseignantId) {
        return ResponseEntity.ok(
                moduleService.getModulesParEnseignant(enseignantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDTO> getModuleParId(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                moduleService.getModuleParIdDTO(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> modifierModule(
            @PathVariable Long id,
            @RequestBody ModuleDTO dto) {
        return ResponseEntity.ok(
                moduleService.modifierModule(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerModule(
            @PathVariable Long id) {
        moduleService.supprimerModule(id);
        return ResponseEntity.ok("Module supprimé");
    }
}