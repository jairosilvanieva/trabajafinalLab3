package Models.Empleados;

import java.io.Serializable;
import java.util.Scanner;


public class Cajero extends Empleado implements Serializable {
    private int horasTrabajadas;

    public Cajero(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono, double salario, String fechaIngreso, int horasTrabajadas) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono, salario, fechaIngreso);
        this.horasTrabajadas = 0;
    }
    public Cajero(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono, String fechaIngreso, int horasTrabajadas) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono, fechaIngreso);
        this.horasTrabajadas = 0;
    }
    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public double calcularSueldo() {

        double precioHora = 1000;
        return this.horasTrabajadas * precioHora;
    }

    public void calcularYAsignarSueldo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese las horas trabajadas: ");
        int horasTrabajadas = scanner.nextInt();
        setHorasTrabajadas(horasTrabajadas);
        double sueldo = calcularSueldo();
        setSueldo(sueldo);
        System.out.println("Sueldo calculado y asignado correctamente: $" + sueldo);
    }
}

