package mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia;

import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;

import javax.persistence.*;

@Entity
public class ImagenIncidencia {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false, columnDefinition = "blob")
    private byte[] imagen;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;
}
