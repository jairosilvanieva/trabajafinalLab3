package Models.Animales;

import Enums.RazaPerro;
import Models.Animales.Animal;

import java.io.Serializable;
import java.time.LocalDate;

public class Perro extends Animal implements Serializable {
    private RazaPerro raza;

    public Perro(String id, String nombre, LocalDate fechaNacimiento, String sexo, double peso, boolean castrado, RazaPerro raza) {
        super(id, nombre, fechaNacimiento, sexo, peso, castrado);
        this.raza = raza;
    }

    public RazaPerro getRaza() {
        return raza;
    }

    public void setRaza(RazaPerro raza) {
        this.raza = raza;
    }

}
