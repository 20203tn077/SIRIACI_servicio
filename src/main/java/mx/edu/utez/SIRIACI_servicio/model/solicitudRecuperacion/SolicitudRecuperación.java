package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SolicitudRecuperación {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String codigo;
    private Date tiempo_solicitud;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
