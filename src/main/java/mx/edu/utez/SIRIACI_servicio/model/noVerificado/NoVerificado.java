package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class NoVerificado {
    // ID
    // Atributos
    // Llaves foraneas
    // Relaciones de otras tablas con esta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String codigo;
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
