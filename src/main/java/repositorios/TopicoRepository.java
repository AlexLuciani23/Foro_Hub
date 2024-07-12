package repositorios;

import entidades.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
