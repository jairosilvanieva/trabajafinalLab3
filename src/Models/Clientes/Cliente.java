package Models.Clientes;

import Models.Animales.Animal;
import Models.Contenedora.Ticket;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cliente extends Persona implements Serializable {
    private ArrayList<Animal> animales;
    private ArrayList<Ticket> tickets;

    public Cliente(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono) {
        super(nombre, apellido, dni, email, direccion, numeroTelefono);
        this.animales = new ArrayList<Animal>();
        this.tickets = new ArrayList<Ticket>();
    }

    public void muestramascota() {
        for (Animal animal : this.animales) {
            System.out.println(animal.toString());
        }
    }
    public ArrayList<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(ArrayList<Animal> animales) {
        this.animales = animales;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void agregarTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public Ticket getTicketDelDia() {
        LocalDateTime now = LocalDateTime.now();

        for (Ticket ticket : this.tickets) {
            if (ticket.getFecha().toLocalDate().equals(now.toLocalDate())) {
                return ticket;
            }
        }

        Ticket nuevoTicket = new Ticket(now.toLocalDate(), now.toLocalTime());
        this.tickets.add(nuevoTicket);
        return nuevoTicket;
    }

    public Animal getAnimal(String idAnimal) {
        for (Animal animal : this.animales) {
            if (animal.getId().equals(idAnimal)) {
                return animal;
            }
        }
        return null;
    }

    public void agregarAnimal(Animal animal) {
        animales.add(animal);
    }

    public void quitarAnimal(String nombreMascota) {
        Animal mascotaEliminar = null;
        for (Animal animal : animales) {
            if (animal.getNombre().equalsIgnoreCase(nombreMascota)) {
                mascotaEliminar = animal;
                break;
            }
        }

        if (mascotaEliminar != null) {
            animales.remove(mascotaEliminar);
        }
    }

}
