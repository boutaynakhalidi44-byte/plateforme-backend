package com.platforme.plateforme_pedagogique.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class JwtUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtUtil.class);

    private static final String SECRET =
            "plateforme_pedagogique_secret_key_2024_very_long";
    private static final long EXPIRATION = 86400000; // 24h

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // توليد Token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    // ✅ استخراج email — مع try-catch
    public String extractEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            logger.warn("⏰ Token expiré: {}", e.getMessage());
            return null;
        } catch (UnsupportedJwtException e) {
            logger.warn("❌ Token non supporté: {}", e.getMessage());
            return null;
        } catch (MalformedJwtException e) {
            logger.warn("❌ Token malformé: {}", e.getMessage());
            return null;
        } catch (JwtException e) {
            logger.warn("❌ Token invalide: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.warn("❌ Erreur Token: {}", e.getMessage());
            return null;
        }
    }

    // ✅ Validation — avec try-catch
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("⏰ Token expiré");
            return false;
        } catch (JwtException e) {
            logger.warn("❌ Token invalide: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.warn("❌ Erreur validation: {}", e.getMessage());
            return false;
        }
    }
}