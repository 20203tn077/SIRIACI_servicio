package mx.edu.utez.SIRIACI_servicio.controller;

public class ImagenDTO {
    // Solo se llena si se va a eliminar
    private Long id;
    // Solo se llena si se va a registrar
    private String imagen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
