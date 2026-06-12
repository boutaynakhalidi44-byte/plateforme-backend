-- Script SQL pour initialiser les données du footer
-- À exécuter dans MySQL après que Hibernate ait créé la table

-- Vérifier si des données existent déjà
SELECT COUNT(*) as nombre_footers FROM footer;

-- Insérer les données par défaut (si la table est vide)
INSERT INTO footer (nom, email, bureau, horaires, created_at, updated_at)
SELECT 'Enseignant responsable', 'contact@plateforme.ma', 'FST Settat', 'Lun–Ven, 9h00 – 17h00', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM footer);

-- Afficher les données
SELECT * FROM footer;
