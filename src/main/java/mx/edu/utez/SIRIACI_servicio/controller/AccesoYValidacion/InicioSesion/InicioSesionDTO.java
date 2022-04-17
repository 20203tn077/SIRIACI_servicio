package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.InicioSesion;

public class InicioSesionDTO {
    private String correo;
    private String contrasena;
    private String dispositivoMovil;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDispositivoMovil() {
        return dispositivoMovil;
    }

    public void setDispositivoMovil(String dispositivoMovil) {
        this.dispositivoMovil = dispositivoMovil;
    }
}
