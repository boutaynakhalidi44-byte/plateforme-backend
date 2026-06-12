package com.platforme.plateforme_pedagogique.service;

import com.platforme.plateforme_pedagogique.entity.Footer;
import com.platforme.plateforme_pedagogique.repository.FooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FooterConfigService {

    @Autowired
    private FooterRepository footerRepository;

    public Footer getFooter() {
        Optional<Footer> footer = footerRepository.findFirstByOrderByIdAsc();
        return footer.orElse(null);
    }

    public Footer saveFooter(Footer footer) {
        Optional<Footer> existingFooter = footerRepository.findFirstByOrderByIdAsc();
        if (existingFooter.isPresent()) {
            Footer existing = existingFooter.get();
            existing.setNom(footer.getNom());
            existing.setEmail(footer.getEmail());
            existing.setBureau(footer.getBureau());
            existing.setHoraires(footer.getHoraires());
            return footerRepository.save(existing);
        } else {
            return footerRepository.save(footer);
        }
    }

    public Footer updateFooter(Long id, Footer footerDetails) {
        Optional<Footer> footer = footerRepository.findById(id);
        if (footer.isPresent()) {
            Footer existingFooter = footer.get();
            existingFooter.setNom(footerDetails.getNom());
            existingFooter.setEmail(footerDetails.getEmail());
            existingFooter.setBureau(footerDetails.getBureau());
            existingFooter.setHoraires(footerDetails.getHoraires());
            return footerRepository.save(existingFooter);
        }
        return null;
    }
}
