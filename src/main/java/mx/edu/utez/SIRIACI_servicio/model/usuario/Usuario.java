package mx.edu.utez.SIRIACI_servicio.model.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.bloqueo.Bloqueo;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificado;
import mx.edu.utez.SIRIACI_servicio.model.notificacion.Notificacion;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion.SolicitudRecuperación;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Atributos
    private long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido1;
    private String apellido2;
    @Column(nullable = false, unique = true)
    private String correo;
    @Column(nullable = false, unique = true)
    private String telefono;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false)
    private boolean activo;
    @Column(nullable = false)
    private boolean comunidadUtez;

    // Relaciones de otras tablas con esta
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Administrador admnistrador;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Estudiante estudiante;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Bloqueo bloqueo;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private NoVerificado noVerificado;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Responsable responsable;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Incidencia> incidencias;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Notificacion> notificaciones;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private SolicitudRecuperación solicitudRecuperación;
}
