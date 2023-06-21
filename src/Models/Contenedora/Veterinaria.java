package Models.Contenedora;

import Models.Animales.Animal;
import Models.Animales.HistoriaClinica;
import Models.Clientes.Cliente;
import Models.Empleados.Empleado;
import Models.Empleados.Veterinario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Veterinaria implements Serializable {

    private HashMap<String, Cliente> clientes;
    private HashMap<String, Empleado> empleados;
    private Insumos insumos;

    public Veterinaria() {
        this.clientes = new HashMap<String, Cliente>();
        this.empleados = new HashMap<String, Empleado>();
        this.insumos = new Insumos();
    }
    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }
    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }
    public void agregarCliente(Cliente cliente) throws Exception {
        if (this.clientes.containsKey(cliente.getDni())) {
            throw new Exception("El cliente ya existe.");
        } else {
            this.clientes.put(cliente.getDni(), cliente);
        }
    }



    public void eliminarCliente(String dni) throws Exception {
        if (!this.clientes.containsKey(dni)) {
            throw new Exception("El cliente no existe.");
        } else {
            this.clientes.remove(dni);
        }
    }

    public void modificarCliente(Cliente cliente) throws Exception {
        if (!this.clientes.containsKey(cliente.getDni())) {
            throw new Exception("El cliente no existe.");
        } else {
            this.clientes.put(cliente.getDni(), cliente);
        }
    }

    public void agregarEmpleado(Empleado empleado) throws Exception {
        if (this.empleados.containsKey(empleado.getDni())) {
            throw new Exception("El empleado ya existe.");
        } else {
            this.empleados.put(empleado.getDni(), empleado);
        }
    }

    public void eliminarEmpleado(String dni) throws Exception {
        if (!this.empleados.containsKey(dni)) {
            throw new Exception("El empleado no existe.");
        } else {
            this.empleados.remove(dni);
        }
    }

    public void modificarEmpleado(Empleado empleado) throws Exception {
        if (!this.empleados.containsKey(empleado.getDni())) {
            throw new Exception("El empleado no existe.");
        } else {
            this.empleados.put(empleado.getDni(), empleado);
        }
    }

    public Boolean buscaMascota(Cliente cl,String nombre){
        for (Animal ani : cl.getAnimales()){
            if (ani.getNombre().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }

    public void realizarServicio(String dniCliente, String dniVeterinario, String servicio) throws Exception {
        Cliente cliente = this.clientes.get(dniCliente);
        Empleado empleado = this.empleados.get(dniVeterinario);

        if (cliente == null) {
            throw new Exception("El cliente no existe.");
        }

        if (empleado == null || !(empleado instanceof Veterinario)) {
            throw new Exception("El empleado no existe o no es un veterinario.");
        }

        Veterinario veterinario = (Veterinario) empleado;
        double precioServicio = 0;


        System.out.println("Seleccione la mascota:");
        int contador = 1;
        for (Animal mascota : cliente.getAnimales()) {
            System.out.println(contador + ". " + mascota.getNombre());
            contador++;
        }


        Scanner scanner = new Scanner(System.in);
        int opcionMascota = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Animal mascotaSeleccionada = cliente.getAnimales().get(opcionMascota - 1);


        switch (servicio) {
            case "vacunar":
                if (!this.insumos.isVacunaDisponible()) {
                    throw new Exception("No hay vacunas disponibles.");
                }
                this.insumos.usarVacuna();
                precioServicio = 5000;
                break;
            case "castrar":
                if (!this.insumos.isMaterialCastracionDisponible()) {
                    throw new Exception("No hay material de castración disponible.");
                }
                this.insumos.usarMaterialCastracion();
                precioServicio = 20000;
                break;
            case "consulta":
                if (!this.insumos.isGuantesDisponibles()) {
                    throw new Exception("No hay guantes disponibles.");
                }
                this.insumos.usarGuantes();
                precioServicio = 4500;
                break;
            default:
                throw new Exception("Servicio no válido.");
        }


        veterinario.realizarServicio(servicio);


        Ticket ticketDelDia = cliente.getTicketDelDia();
        ticketDelDia.agregarServicio(servicio, precioServicio);


        HistoriaClinica nuevaHistoria = new HistoriaClinica(LocalDateTime.now(), servicio);
        mascotaSeleccionada.agregarHistoriaClinica(nuevaHistoria);


        mascotaSeleccionada.guardarHistoriaClinica("historiaclinica.dat");


    }
    public Cliente getCliente(String dni) {
        return clientes.get(dni);
    }
    public Empleado getEmpleado(String dni) {
        return empleados.get(dni);
    }

    public Veterinario getVeterinarioByDni(String dni) {
        Empleado empleado = getEmpleado(dni);
        if (empleado instanceof Veterinario) {
            return (Veterinario) empleado;
        }
        return null;
    }
    public Boolean existeEmpleado(String dni){
        return empleados.containsKey(dni);
    }

}
