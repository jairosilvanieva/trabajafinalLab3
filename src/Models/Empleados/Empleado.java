package Models.Empleados;

import Models.Clientes.Persona;
import Interfacez.Sueldo;

import java.io.Serializable;

public abstract class Empleado extends Persona implements Sueldo, Serializable {
    protected double salario;
    protected String fechaIngreso;
    protected double sueldo;

    public Empleado(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono, double salario, String fechaIngreso) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono);
        this.salario = salario;
        this.fechaIngreso = fechaIngreso;
        this.sueldo = 0;
    }

    public Empleado(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono, String fechaIngreso) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono);
        this.fechaIngreso = fechaIngreso;
    }


    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }


    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public double getSueldo() {
        return sueldo;
    }


    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public abstract double calcularSueldo();

    public abstract void calcularYAsignarSueldo();

    @Override
    public String toString() {
        return "Empleado{" +
                "salario=" + salario +
                ", fechaIngreso='" + fechaIngreso + '\'' +
                "} " + super.toString();
    }
}
