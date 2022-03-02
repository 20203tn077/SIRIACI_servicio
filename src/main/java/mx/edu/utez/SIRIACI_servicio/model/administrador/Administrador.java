package mx.edu.utez.SIRIACI_servicio.model.administrador;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Administrador {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

}
