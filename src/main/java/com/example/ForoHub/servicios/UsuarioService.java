package com.example.ForoHub.servicios;
// Importación corregida

import entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.UsuarioRepository;


@Service
public class UsuarioService {
    // En la clase UsuarioService
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Autowired
    private UsuarioRepository usuarioRepository;

    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    // Agrega más métodos según las funcionalidades que quieras implementar
}


