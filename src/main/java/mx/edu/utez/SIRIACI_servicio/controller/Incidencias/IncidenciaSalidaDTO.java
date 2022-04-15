package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import java.time.LocalDateTime;

public class IncidenciaSalidaDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime tiempoIncidencia;
    private Boolean activo;
    private Importancia importancia;
    private Estado estado;
    private String aspecto;

    public IncidenciaSalidaDTO(Incidencia incidencia) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.tiempoIncidencia = incidencia.getTiempoIncidencia();
        this.activo = incidencia.isActivo();
        this.importancia = incidencia.getImportancia();
        this.estado = incidencia.getEstado();
        this.aspecto = incidencia.getAspecto().getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getTiempoIncidencia() {
        return tiempoIncidencia;
    }

    public void setTiempoIncidencia(LocalDateTime tiempoIncidencia) {
        this.tiempoIncidencia = tiempoIncidencia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Importancia getImportancia() {
        return importancia;
    }

    public void setImportancia(Importancia importancia) {
        this.importancia = importancia;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getAspecto() {
        return aspecto;
    }

    public void setAspecto(String aspecto) {
        this.aspecto = aspecto;
    }
}
