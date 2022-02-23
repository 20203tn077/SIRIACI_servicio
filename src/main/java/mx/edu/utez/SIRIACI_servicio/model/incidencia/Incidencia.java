package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import java.util.Date;

public class Incidencia {
    private long id;
    private String descripcion;
    private Date tiempoIncidencia;
    private double longitud;
    private double latitud;
    private boolean activo;
    private String comentario;
    private Importancia importancia;
    private Estado estado;
    private Aspecto aspecto;
    private Usuario usuario;
}
