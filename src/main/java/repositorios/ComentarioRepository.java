package repositorios;

import entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
