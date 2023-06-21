package Models.Clientes;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected String email;
    protected String direccion;
    protected String numeroTelefono;

    public Persona(String nombre, String apellido, String dni, String email, String direccion, String numeroTelefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "\nnombre='" + nombre + '\'' +
                "\napellido='" + apellido + '\'' +
                "\ndni='" + dni + '\'' +
                "\nemail='" + email + '\'' +
                "\ndireccion='" + direccion + '\'' +
                "\nnumeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}
