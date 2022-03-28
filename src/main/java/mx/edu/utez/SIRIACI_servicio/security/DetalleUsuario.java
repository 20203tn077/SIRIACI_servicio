package mx.edu.utez.SIRIACI_servicio.security;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DetalleUsuario implements UserDetails {
    private long id;
    private String correo;
    private String contrasena;
    private Collection<? extends GrantedAuthority> authorities;

    public DetalleUsuario(long id, String correo, String contrasena, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.authorities = authorities;
    }

    public static DetalleUsuario build(Usuario usuario){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USUARIO"));
        if (usuario.getAdministrador() != null) authorities.add(new SimpleGrantedAuthority("ADMINISTRADOR"));
        if (usuario.getResponsable() != null) authorities.add(new SimpleGrantedAuthority("RESPONSABLE"));
        return new DetalleUsuario(usuario.getId(), usuario.getCorreo(), usuario.getContrasena(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
