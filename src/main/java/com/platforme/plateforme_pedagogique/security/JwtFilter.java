package com.platforme.plateforme_pedagogique.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // ✅ استخراج Token من Header
            String authHeader =
                    request.getHeader("Authorization");

            // ✅ Log — للDebug
            logger.debug("📥 Request: {} {}",
                    request.getMethod(), request.getRequestURI());

            String token = null;
            String email = null;

            // تحقق من Header
            if (authHeader != null &&
                    authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                logger.debug("🔑 Token trouvé");

                // ✅ extractEmail مع try-catch داخل JwtUtil
                email = jwtUtil.extractEmail(token);

                if (email == null) {
                    logger.warn("⚠️ Email non extrait du token");
                }
            } else {
                logger.debug("⚠️ Pas de token dans le header");
            }

            // ✅ Authentification
            if (email != null &&
                    SecurityContextHolder.getContext()
                            .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    // ✅ Set dans SecurityContext
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);

                    logger.debug("✅ Utilisateur authentifié: {}",
                            email);
                } else {
                    logger.warn("❌ Token invalide pour: {}",
                            email);
                }
            }

        } catch (Exception e) {
            // ✅ On n'interrompt pas la chaîne
            logger.error("❌ Erreur dans JwtFilter: {}",
                    e.getMessage());
            // ✅ On nettoie le SecurityContext
            SecurityContextHolder.clearContext();
        }

        // ✅ Toujours continuer la chaîne
        filterChain.doFilter(request, response);
    }
}