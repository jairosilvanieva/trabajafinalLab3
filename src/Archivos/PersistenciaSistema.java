package Archivos;

import Models.Contenedora.Veterinaria;

import java.io.*;

public class PersistenciaSistema {
    private static final String NOMBRE_ARCHIVO = "veterinaria.dat";

    public static Veterinaria cargarVeterinaria() {
        Veterinaria veterinaria = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOMBRE_ARCHIVO))) {
            veterinaria = (Veterinaria) ois.readObject();
            System.out.println("Veterinaria cargada correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de veterinaria no encontrado. Se creará uno nuevo.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de veterinaria: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase Veterinaria no encontrada: " + e.getMessage());
        }

        return veterinaria;
    }

    public static void guardarVeterinaria(Veterinaria veterinaria) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            oos.writeObject(veterinaria);
            System.out.println("Veterinaria guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la veterinaria: " + e.getMessage());
        }
    }
}