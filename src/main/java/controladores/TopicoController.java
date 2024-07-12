package controladores;

import entidades.Topico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repositorios.TopicoRepository;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public List<Topico> obtenerTodosLosTopicos() {
        return topicoRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')") // Solo usuarios autenticados pueden crear tópicos
    public Topico crearTopico(@RequestBody Topico topico) {
        return topicoRepository.save(topico);
    }

    @GetMapping("/{id}")
    public Topico obtenerTopicoPorId(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') and #topico.usuario.id == authentication.principal.id") // Solo el creador del tópico puede editarlo
    public Topico actualizarTopico(@PathVariable Long id, @RequestBody Topico topicoActualizado) {
        Topico topicoExistente = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        // Actualiza los atributos del tópico existente
        topicoExistente.setTitulo(topicoActualizado.getTitulo());
        topicoExistente.setMensaje(topicoActualizado.getMensaje());
        // ... (otros atributos)

        return topicoRepository.save(topicoExistente); // <-- Sentencia return agregada
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #topico.usuario.id == authentication.principal.id)") // Administradores o el creador del tópico pueden eliminarlo
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        try {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para realizar esta acción.");
        }
    }
}
