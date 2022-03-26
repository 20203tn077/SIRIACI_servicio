package mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia;

import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;

import javax.persistence.*;

@Entity
public class ImagenIncidencia {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, columnDefinition = "blob")
    private Byte[] imagen;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incidencia incidencia;

    public ImagenIncidencia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte[] getImagen() {
        return imagen;
    }

    public void setImagen(Byte[] imagen) {
        this.imagen = imagen;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }
}
