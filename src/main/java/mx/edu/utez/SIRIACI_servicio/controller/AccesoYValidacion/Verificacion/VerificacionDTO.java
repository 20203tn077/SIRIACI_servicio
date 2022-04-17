package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Verificacion;

import java.util.UUID;

public class VerificacionDTO {
    private String correo;
    private UUID codigo;

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
