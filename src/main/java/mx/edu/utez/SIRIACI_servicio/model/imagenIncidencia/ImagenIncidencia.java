package mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia;

import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class ImagenIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@Column(name = "imagen", nullable = false, columnDefinition = "Blob")
    //private Base64 imagen;
    @ManyToOne
    @JoinColumn(name = "incidencia_id", nullable = false, unique = true)
    private Incidencia incidencia;
}
