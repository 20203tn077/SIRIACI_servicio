package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Incidencia {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Date tiempoIncidencia;
    @Column(nullable = false)
    private double longitud;
    @Column(nullable = false)
    private double latitud;
    @Column(nullable = false)
    private boolean activo;
    @Column(length = 128)
    private String comentario;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "importancia_id",  nullable = false)
    private Importancia importancia;
    @ManyToOne
    @JoinColumn(name = "estado_id",  nullable = false)
    private Estado estado;
    @ManyToOne
    @JoinColumn(name = "aspecto_id",  nullable = false)
    private Aspecto aspecto;
    @ManyToOne
    @JoinColumn(name = "usuario_id",  nullable = false)
    private Usuario usuario;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "incidencia")
    @JsonIgnore
    private List<ImagenIncidencia> imagenesIncidencia;
}
