package Models.Contenedora;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Ticket implements Serializable {
    private LocalDateTime fecha;
    private String hora;
    private ArrayList<String> tipoServicios;
    private ArrayList<Double> preciosServicios;
    private double total;

    public Ticket(LocalDate fecha, LocalTime hora) {
        this.fecha = fecha.atStartOfDay();
        this.hora = String.valueOf(hora);
        this.tipoServicios = new ArrayList<>();
        this.preciosServicios = new ArrayList<>();
        this.total = 0;
    }

    public void agregarServicio(String servicio, double precio) {
        this.tipoServicios.add(servicio);
        this.preciosServicios.add(precio);
        this.total += precio;
    }



    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public ArrayList<String> getTipoServicios() {
        return tipoServicios;
    }

    public void setTipoServicios(ArrayList<String> tipoServicios) {
        this.tipoServicios = tipoServicios;
    }

    public ArrayList<Double> getPreciosServicios() {
        return preciosServicios;
    }

    public void setPreciosServicios(ArrayList<Double> preciosServicios) {
        this.preciosServicios = preciosServicios;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
