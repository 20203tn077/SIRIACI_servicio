package mx.edu.utez.SIRIACI_servicio.model.imagenCapsula;

import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class ImagenCapsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, columnDefinition = "blob")
    private Byte[] imagen;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "capsula_id", nullable = false)
    private Capsula capsula;

    public ImagenCapsula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
