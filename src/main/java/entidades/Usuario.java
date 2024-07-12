package entidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import java.util.HashSet;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombreUsuario;
    private String correoElectronico;
    private String contraseña;
    @ManyToMany(fetch = FetchType.EAGER) // Cargar los roles al obtener el usuario
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>(); // Inicializar el conjunto de roles
    // Método auxiliar para agregar roles
    public void agregarRol(Rol rol) {
        this.roles.add(rol);
    }

}

