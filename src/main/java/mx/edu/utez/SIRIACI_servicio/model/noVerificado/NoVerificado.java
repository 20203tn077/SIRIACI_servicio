package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class NoVerificado {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
