package controladores; // Si quieres mover la clase al paquete de pruebas, cambia esto a "com.example.ForoHub.test"

import org.junit.jupiter.api.Test;
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
public class AdminControllerIntegracionTest { // Nombre de la clase cambiado

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void asignarRolAUsuario_usuarioSinPermiso_deberiaDevolverForbidden() throws Exception {
        Long usuarioId = 1L;
        Long rolId = 2L;

        mockMvc.perform(post("/admin/usuarios/{usuarioId}/roles/{rolId}", usuarioId, rolId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Cuerpo vacío
                .andExpect(status().isForbidden());
    }
}
