package mx.edu.utez.SIRIACI_servicio.model.capsula;

import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;

import javax.persistence.*;

@Entity
public class Capsula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String contenido; //text
    private boolean activo;
    @ManyToOne
    @JoinColumn( name = "responsable_id",nullable = false, unique = true)
    private Responsable responsable;
}
