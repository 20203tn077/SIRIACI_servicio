package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class NoVerificado {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String codigo;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public NoVerificado(String codigo, Usuario usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
    }

    public NoVerificado() {
    }
}
