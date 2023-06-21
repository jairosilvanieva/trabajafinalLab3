package Models.Animales;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HistoriaClinica implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime fecha;
    private String servicio;

    public HistoriaClinica(LocalDateTime fecha, String servicio) {
        this.fecha = fecha;
        this.servicio = servicio;
    }



    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}