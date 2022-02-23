package mx.edu.utez.SIRIACI_servicio.model.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.bloqueo.Bloqueo;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificado;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String telefono;
    private String contrasena;
    private boolean activo;
    private boolean comunidadUtez;

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


}
