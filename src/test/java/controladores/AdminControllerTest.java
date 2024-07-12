package controladores;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void asignarRoles_usuarioAdministrador_deberiaPermitirAcceso() throws Exception {
        // Prepara los datos de la solicitud (ID del usuario, roles a asignar)
        String jsonBody = "{\"nombresRoles\": [\"ROLE_USER\"]}"; // Ejemplo

        // Realiza la solicitud POST al endpoint /admin/usuarios/{id}/roles
        mockMvc.perform(post("/admin/usuarios/1/roles").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().is3xxRedirection()); // Verifica redirección (3xx)
    }

    @Test
    @WithMockUser(roles = "USER")
    public void asignarRoles_usuarioNoAdministrador_deberiaDenegarAcceso() throws Exception {
        // Prepara los datos de la solicitud (ID del usuario, roles a asignar)
        String jsonBody = "{\"nombresRoles\": [\"ROLE_USER\"]}"; // Ejemplo
        // Realiza la solicitud POST al endpoint /admin/usuarios/{id}/roles
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/usuarios/1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isForbidden()); // Verifica acceso denegado (403)
    }
}
