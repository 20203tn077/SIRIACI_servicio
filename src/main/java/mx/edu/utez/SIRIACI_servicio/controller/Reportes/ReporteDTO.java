package mx.edu.utez.SIRIACI_servicio.controller.Reportes;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReporteDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    private Byte aspecto;
    private List<Byte> aspectos;

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Byte getAspecto() {
        return aspecto;
    }

    public void setAspecto(Byte aspecto) {
        this.aspecto = aspecto;
    }

    public List<Byte> getAspectos() {
        return aspectos;
    }

    public void setAspectos(List<Byte> aspectos) {
        this.aspectos = aspectos;
    }
}
