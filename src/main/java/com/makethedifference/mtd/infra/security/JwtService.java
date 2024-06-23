package com.makethedifference.mtd.infra.security;

import com.makethedifference.mtd.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY ="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Genera un token JWT para el usuario dado.
     *
     * @param user    Detalles del usuario.
     * @param usuario Entidad Usuario.
     * @return Token JWT.
     */
    public String getToken(UserDetails user, Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", usuario.getId());
        claims.put("role", usuario.getRol().name());
        claims.put("nombre", usuario.getNombre());
        claims.put("telefono", usuario.getTelefono());
        claims.put("correo", usuario.getCorreo());

        return generateToken(claims, user.getUsername());
    }

    /**
     * Genera un token JWT con los claims y el sujeto dados.
     *
     * @param claims  Claims del token.
     * @param subject Sujeto del token.
     * @return Token JWT.
     */
    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validez
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene la clave de firma del token.
     *
     * @return Clave de firma.
     */
    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extrae el nombre de usuario del token JWT.
     *
     * @param token Token JWT.
     * @return Nombre de usuario.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si el token es válido para los detalles del usuario dados.
     *
     * @param token       Token JWT.
     * @param userDetails Detalles del usuario.
     * @return Verdadero si el token es válido, falso en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Obtiene todos los claims del token JWT.
     *
     * @param token Token JWT.
     * @return Claims del token.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrae un claim específico del token JWT usando un resolver.
     *
     * @param token          Token JWT.
     * @param claimsResolver Función para resolver el claim.
     * @param <T>            Tipo del claim.
     * @return Claim extraído.
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene la fecha de expiración del token JWT.
     *
     * @param token Token JWT.
     * @return Fecha de expiración.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token Token JWT.
     * @return Verdadero si el token ha expirado, falso en caso contrario.
     */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
