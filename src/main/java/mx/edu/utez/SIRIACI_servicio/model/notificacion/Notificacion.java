package mx.edu.utez.SIRIACI_servicio.model.notificacion;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String descripcion;
    @ManyToOne
    @JoinColumn( name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
