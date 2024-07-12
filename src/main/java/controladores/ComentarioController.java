package controladores;

import entidades.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repositorios.ComentarioRepository;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @GetMapping
    @PreAuthorize("permitAll()") // Todos pueden ver los comentarios
    public List<Comentario> obtenerTodosLosComentarios() {
        return comentarioRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')") // Solo usuarios autenticados pueden crear comentarios
    public Comentario crearComentario(@RequestBody Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()") // Todos pueden ver un comentario específico
    public Comentario obtenerComentarioPorId(@PathVariable Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or #comentario.usuario.id == authentication.principal.id") // Solo el autor, administradores o moderadores pueden editar un comentario
    public Comentario actualizarComentario(@PathVariable Long id, @RequestBody Comentario comentarioActualizado) {
        Comentario comentarioExistente = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        // Actualiza los atributos del comentario existente
        comentarioExistente.setContenido(comentarioActualizado.getContenido());
        // ... (otros atributos)

        return comentarioRepository.save(comentarioExistente);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR') or #comentario.usuario.id == authentication.principal.id")
    // Solo el autor, administradores o moderadores pueden eliminar un comentario
    public ResponseEntity<?> eliminarComentario(@PathVariable Long id) {
        try {
            comentarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para realizar esta acción.");
        }
    }
}

