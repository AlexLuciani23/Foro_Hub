package seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final String INVALID_JWT_TOKEN = "Token JWT inválido: ";
    private static final String EXPIRED_JWT_TOKEN = "Token JWT expirado: ";
    private static final String UNSUPPORTED_JWT_TOKEN = "Token JWT no soportado: ";
    private static final String EMPTY_JWT_CLAIMS = "Reclamos JWT vacíos: ";

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    public String generarTokenJwt(Authentication authentication) {
        String nombreUsuario = authentication.getName();
        Date ahora = new Date();
        Date fechaExpiracion = new Date(ahora.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(ahora)
                .setExpiration(fechaExpiracion)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public String obtenerNombreUsuarioDelTokenJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor
                        (jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarTokenJwt(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            // ... (manejo de excepciones)
        } catch (ExpiredJwtException e) {
            // ... (manejo de excepciones)
        } catch (UnsupportedJwtException e) {
            // ... (manejo de excepciones)
        } catch (IllegalArgumentException e) {
            // ... (manejo de excepciones)
        }
        return false; // <-- Sentencia return para el método validarTokenJwt
    }
}