package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SolicitudRecuperación {
    // ID
    // Atributos
    // Llaves foraneas
    // Relaciones de otras tablas con esta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private Date tiempo_solicitud;
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
