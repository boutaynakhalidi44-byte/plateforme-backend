package com.platforme.plateforme_pedagogique.security;

import com.platforme.plateforme_pedagogique.entity.Utilisateur;
import com.platforme.plateforme_pedagogique.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utilisateur non trouvé: " + email));

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .authorities("ROLE_" + utilisateur.getRole().name())  // ✅ Fix
                .build();
    }
}