package com.example.ForoHub.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import repositorios.UsuarioRepository;
import com.example.ForoHub.servicios.UsuarioService;
import entidades.Usuario;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;
    @MockBean
    private UsuarioRepository usuarioRepositoryMock; // Declaración correcta

    private UsuarioServiceTest() {
    }

    public static UsuarioServiceTest createUsuarioServiceTest() {
        return new UsuarioServiceTest();
    }


    @Test
    public void crearUsuario_deberiaGuardarUsuarioEnRepositorio() {
        // Crea un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Juan");

        Mockito.when(usuarioRepositoryMock.save(usuario)).thenReturn(usuario);

        // Llama al método crearUsuario del servicio
        usuarioService.crearUsuario(usuario);

        // Verifica que el método save del repositorio haya sido llamado una vez
        Mockito.verify(usuarioRepositoryMock, Mockito.times(1)).save(usuario);
    }
}
