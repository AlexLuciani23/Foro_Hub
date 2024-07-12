package seguridad;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validarTokenJwt(jwt)) {
                String username = jwtUtils.obtenerNombreUsuarioDelTokenJwt(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Usuario {} autenticado con token JWT: {}", username, jwt); // Logging más detallado

            }
                } catch (UsernameNotFoundException e) {
                    logger.error("Usuario no encontrado: {}", e.getMessage());
                } catch (Exception e) {
                    logger.error("No se pudo establecer la autenticación del usuario: {}", e.getMessage());
                }
                filterChain.doFilter(request, response);
            }

            private String parseJwt(HttpServletRequest request) {
                //Extrae el token JWT del encabezado Autorization
                String headerAuth = request.getHeader("Authorization");

                if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                    return headerAuth.substring(7, headerAuth.length());
                }
                return null;
            }
}

