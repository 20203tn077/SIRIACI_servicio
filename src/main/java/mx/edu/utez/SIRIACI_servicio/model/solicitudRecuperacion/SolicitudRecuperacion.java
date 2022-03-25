package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SolicitudRecuperacion {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private Date tiempo_solicitud;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public SolicitudRecuperacion() {
    }
}
