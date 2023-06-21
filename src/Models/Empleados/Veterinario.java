package Models.Empleados;

import Models.Empleados.Empleado;

import java.io.Serializable;
import java.util.List;

public class Veterinario extends Empleado implements Serializable {
    private List<String> serviciosHechos;
    private List<String> turnos;
    private boolean disponible;

    public Veterinario(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono, double salario, String fechaIngreso, List<String> serviciosHechos, List<String> turnos, boolean disponible) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono, salario, fechaIngreso);
        this.serviciosHechos = serviciosHechos;
        this.turnos = turnos;
        this.disponible = disponible;
    }
    public Veterinario(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono, String fechaIngreso, List<String> serviciosHechos, List<String> turnos, boolean disponible) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono, fechaIngreso);
        this.serviciosHechos = serviciosHechos;
        this.turnos = turnos;
        this.disponible = disponible;
    }
    public List<String> getServiciosHechos() {
        return serviciosHechos;
    }

    public void setServiciosHechos(List<String> serviciosHechos) {
        this.serviciosHechos = serviciosHechos;
    }

    public List<String> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<String> turnos) {
        this.turnos = turnos;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void realizarServicio(String servicio) {
        this.serviciosHechos.add(servicio);
    }
    public void agendarTurno(String turno) {
        this.turnos.add(turno);
    }

    @Override
    public double calcularSueldo() {

        double costoTotalServicios = 0;


        for (String servicio : serviciosHechos) {

            double costoServicio = obtenerCostoServicio(servicio);
            costoTotalServicios += costoServicio;
        }


        return costoTotalServicios * 0.3;
    }

    private double obtenerCostoServicio(String servicio) {

        if (servicio.equals("vacunar")) {
            return 5000;
        } else if (servicio.equals("castrar")) {
            return 20000;
        } else if (servicio.equals("consulta")) {
            return 4500;
        } else {
            return 0;
        }
    }
    public void calcularYAsignarSueldo() {
        double sueldo = calcularSueldo();
        setSueldo(sueldo);
        System.out.println("Sueldo calculado y asignado correctamente: $" + sueldo);
    }
}