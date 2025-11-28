package backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {

    @Value("${jwt.clave-token}")
    private String jwtClaveToken;

    @Value("${jwt.tiempo-expiracion}")
    private long jwtTiempoExpiracion;

    private Key key;
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtClaveToken.getBytes(StandardCharsets.UTF_8));
    }

    public String generarToken(String nombreUsuario) {
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtTiempoExpiracion))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String nombreUsuarioToken(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public boolean validarToken(String token) {
        try {
            if (blacklistedTokens.contains(token)) {
                return false;
            }
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public void invalidarToken(String token) {
        blacklistedTokens.add(token);
    }

    public Date getExpirationDateFromToken(String token) {
        return parseClaims(token).getBody().getExpiration();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public void limpiarTokensExpirados() {
        blacklistedTokens.removeIf(token -> {
            try {
                return isTokenExpired(token);
            } catch (Exception e) {
                return true; // Remover tokens malformados
            }
        });
    }

    public Jws<Claims> parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
