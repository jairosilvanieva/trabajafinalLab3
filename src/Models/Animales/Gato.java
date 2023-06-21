package Models.Animales;

import Enums.RazaGato;
import Models.Animales.Animal;

import java.io.Serializable;
import java.time.LocalDate;

public class Gato extends Animal implements Serializable {
    private RazaGato raza;

    public Gato(String id, String nombre, LocalDate fechaNacimiento, String sexo, double peso, boolean castrado, RazaGato raza) {
        super(id, nombre, fechaNacimiento, sexo, peso, castrado);
        this.raza = raza;
    }

    public RazaGato getRaza() {
        return raza;
    }

    public void setRaza(RazaGato raza) {
        this.raza = raza;
    }

}
