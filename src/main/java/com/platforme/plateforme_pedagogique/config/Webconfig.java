package com.platforme.plateforme_pedagogique.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    /**
     * ✅ Sert le dossier uploads/ local via l'URL /uploads/**
     * Résout l'erreur HTTP 403 lors du téléchargement des fichiers.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}