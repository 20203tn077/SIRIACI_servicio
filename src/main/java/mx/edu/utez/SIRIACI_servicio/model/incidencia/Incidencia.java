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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "descripcion", length = 256, nullable = false)
    private String descripcion;
    @Column(name = "tiempoIncidencia", nullable = false)
    private Date tiempoIncidencia;
    @Column(name = "longitud", nullable = false)
    private double longitud;
    @Column(name = "latitud", nullable = false)
    private double latitud;
    @Column(name = "activo", nullable = false)
    private boolean activo;
    @Column(name = "comentario", length = 128)
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "importancia_id",  nullable = false, unique = true)
    private Importancia importancia;
    @ManyToOne
    @JoinColumn(name = "estado_id",  nullable = false, unique = true)
    private Estado estado;
    @OneToMany(mappedBy = "incidencia")
    @JsonIgnore
    private List<ImagenIncidencia> imagenesIncidencia;
    @ManyToOne
    @JoinColumn(name = "aspecto_id",  nullable = false, unique = true)
    private Aspecto aspecto;
    @ManyToOne
    @JoinColumn(name = "usuario_id",  nullable = false, unique = true)
    private Usuario usuario;
}
