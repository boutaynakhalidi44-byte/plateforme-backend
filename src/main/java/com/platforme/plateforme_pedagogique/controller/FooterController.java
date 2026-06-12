package com.platforme.plateforme_pedagogique.controller;

import com.platforme.plateforme_pedagogique.entity.Footer;
import com.platforme.plateforme_pedagogique.service.FooterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/footer")
@CrossOrigin(origins = "*")
public class FooterController {

    @Autowired
    private FooterConfigService footerConfigService;

    @GetMapping
    public ResponseEntity<Footer> getFooter() {
        Footer footer = footerConfigService.getFooter();
        if (footer == null) {
            footer = new Footer();
            footer.setNom("");
            footer.setEmail("");
            footer.setBureau("");
            footer.setHoraires("");
        }
        return ResponseEntity.ok(footer);
    }

    @PostMapping
    public ResponseEntity<Footer> saveFooter(@RequestBody Footer footer) {
        Footer savedFooter = footerConfigService.saveFooter(footer);
        return ResponseEntity.ok(savedFooter);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Footer> updateFooter(@PathVariable Long id, @RequestBody Footer footer) {
        Footer updatedFooter = footerConfigService.updateFooter(id, footer);
        return ResponseEntity.ok(updatedFooter);
    }
}
