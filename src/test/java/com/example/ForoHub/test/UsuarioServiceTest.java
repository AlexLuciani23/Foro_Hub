package com.example.ForoHub.test;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import repositorios.UsuarioRepository;
import com.example.ForoHub.servicios.UsuarioService;
import entidades.Usuario;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private UsuarioService usuarioService;
    @
            MockBean
    private UsuarioRepository usuarioRepositoryMock;

    @Test
    public void crearUsuario_deberiaGuardarUsuarioEnRepositorio() {
        // Crea un usuario de prueba con datos completos
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Juan");
        usuario.setCorreoElectronico("juan@example.com");
        usuario.setContraseña("passwordHasheada"); // Asegúrate de hashear la contraseña

        // Configura el mock del repositorio
        Mockito.when(usuarioRepositoryMock.save(usuario)).thenReturn(usuario);

        // Llama al método crearUsuario del servicio
        Usuario usuarioCreado = usuarioService.crearUsuario(usuario); // Llamada correcta al método

        // Verifica que el método save del repositorio haya sido llamado una vez
        Mockito.verify(usuarioRepositoryMock, Mockito.times(1)).save(usuario);

        // Verifica que el usuario creado tenga los datos esperados
        assertEquals("Juan", usuarioCreado.getNombreUsuario());
        assertEquals("juan@example.com", usuarioCreado.getCorreoElectronico());
        // ... otras verificaciones
    }
}
