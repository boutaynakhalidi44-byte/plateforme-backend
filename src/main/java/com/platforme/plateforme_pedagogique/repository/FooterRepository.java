package com.platforme.plateforme_pedagogique.repository;

import com.platforme.plateforme_pedagogique.entity.Footer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooterRepository extends JpaRepository<Footer, Long> {
    
    /**
     * Récupère le premier footer (généralement il n'y en a qu'un)
     */
    Optional<Footer> findFirstByOrderByIdAsc();
}
