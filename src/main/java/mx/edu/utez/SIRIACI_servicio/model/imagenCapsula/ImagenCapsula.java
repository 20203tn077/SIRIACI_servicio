package mx.edu.utez.SIRIACI_servicio.model.imagenCapsula;

import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class ImagenCapsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false, columnDefinition = "blob")
    private byte[] imagen;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "capsula_id", nullable = false)
    private Capsula capsula;

    public ImagenCapsula() {
    }
}
