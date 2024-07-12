package controladores;
import entidades.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repositorios.UsuarioRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')") // Solo administradores y moderadores pueden ver todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Solo administradores pueden crear usuarios
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or #id == authentication.principal.id") // Administradores, moderadores o el propio usuario pueden ver un usuario específico
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id") // Administradores o el propio usuario pueden actualizar un usuario
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualiza los atributos del usuario existente con los del usuario actualizado
        usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuarioExistente.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
        // ...

        return usuarioRepository.save(usuarioExistente);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El nombre de usuario ya está en uso!");
        }
        if (usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El correo electrónico ya está en uso!");
        }
        // Guardar el nuevo usuario en la base de datos
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.created(URI.create("/usuarios/" + nuevoUsuario.getId())).body(nuevoUsuario);


    }
}