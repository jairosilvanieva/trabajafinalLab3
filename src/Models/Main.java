package Models;

import Archivos.PersistenciaSistema;
import Enums.RazaGato;
import Enums.RazaPerro;
import Generico.Listados;
import Models.Animales.Animal;
import Models.Animales.Gato;
import Models.Animales.Perro;
import Models.Clientes.Cliente;
import Models.Contenedora.Ticket;
import Models.Contenedora.Veterinaria;
import Models.Empleados.Cajero;
import Models.Empleados.Empleado;
import Models.Empleados.Veterinario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {


    private static void imprimirTicket(Ticket ticket) {
        System.out.println("************* TICKET *************");
        System.out.println("Fecha: " + ticket.getFecha().toLocalDate());
        System.out.println("Hora: " + ticket.getHora());
        System.out.println("------------------------------");
        System.out.println("Servicios:");

        ArrayList<String> tipoServicios = ticket.getTipoServicios();
        ArrayList<Double> preciosServicios = ticket.getPreciosServicios();

        for (int i = 0; i < tipoServicios.size(); i++) {
            String servicio = tipoServicios.get(i);
            double precio = preciosServicios.get(i);
            System.out.println(servicio + ": $" + precio);
        }

        System.out.println("------------------------------");
        System.out.println("Total: $" + ticket.getTotal());
        System.out.println("**********************************");
        System.out.println();
    }
    private static PersistenciaSistema persistencia = new PersistenciaSistema();
    public static Veterinaria veterinaria;
    public static void main(String[] args) {

        veterinaria = PersistenciaSistema.cargarVeterinaria();
        if (veterinaria == null) {
            veterinaria = new Veterinaria();
        }

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        Boolean ingresoValido=false;
        while (!salir) {

            ObjectMapper objectMapper = JsonMapper.builder()
                    .configure(SerializationFeature.INDENT_OUTPUT, true)
                    .build();
            System.out.println("-------------------------------------- Menú Principal ----------------------------------------");
            System.out.println("1. Clientes");
            System.out.println("2. Empleados");
            System.out.println("3. Realizar servicio");
            System.out.println("4. Ver información");
            System.out.println("5. Agendar turno");
            System.out.println("6. Cobrar cliente");
            System.out.println("7. Ver informacion cargada");
            System.out.println("8. Salir");
            System.out.print("Ingrese la opción deseada: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    // Submenú de Clientes
                    boolean salirClientes = false;
                    while (!salirClientes) {
                        System.out.println("------------------------------------ Menú Clientes --------------------------------------------------");
                        System.out.println("1. Agregar cliente");
                        System.out.println("2. Eliminar cliente");
                        System.out.println("3. Modificar cliente");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Ingrese la opción deseada: ");
                        int opcionClientes = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionClientes) {
                            case 1:
                                // Agregar cliente
                                String nombreCliente="",apellidoCliente="",dniCliente="",emailCliente="",direccionCliente="",numeroTelefonoCliente="",fechaNacimientoStr="";
                                String especie="",sexo="",nombreMascota="";
                                Boolean castrado=null;
                                Double peso=0d;
                                Integer opcionModificar=0;
                                Cliente clienteExistente=null;
                                LocalDate fechaNacimiento=null;
                                ingresoValido=false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el DNI del cliente: ");
                                        dniCliente = scanner.nextLine();
                                        if (veterinaria.getClientes().containsKey(dniCliente)){
                                            throw new Exception("El DNI ya se encuentra asignado a un cliente. Ingrese otro");
                                        }
                                        // Comprobar si el DNI contiene solo números
                                        Integer.parseInt(dniCliente);
                                        ingresoValido = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("El DNI debe contener solo números. Por favor, ingréselo nuevamente.");
                                    }catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                }
                                ingresoValido=false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el nombre del cliente: ");
                                        nombreCliente = scanner.nextLine();

                                        if (!nombreCliente.matches("[a-zA-Z]+")) {
                                            throw new Exception("Debe ingresar solo letras.");
                                        }

                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());

                                    }
                                }
                                ingresoValido=false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el apellido del cliente: ");
                                        apellidoCliente = scanner.nextLine();
                                        if (!apellidoCliente.matches("[a-zA-Z]+")) {
                                            throw new Exception("Debe ingresar solo letras.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());

                                    }
                                }

                                ingresoValido=false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el email del cliente: ");
                                        emailCliente = scanner.nextLine();

                                        // Comprobar si el email contiene "@" y ".com"
                                        if (!emailCliente.contains("@") || !emailCliente.endsWith(".com")) {
                                            throw new Exception("El email debe contener '@' y terminar con '.com'.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage() + " Por favor, ingréselo nuevamente.");
                                    }
                                }
                                ingresoValido=false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese la dirección del cliente: ");
                                        direccionCliente = scanner.nextLine();

                                        // Comprobar si la dirección contiene solo números y letras
                                        if (!direccionCliente.matches("[a-zA-Z0-9 ]+")) {
                                            throw new Exception("La dirección debe contener solo letras y números.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage() + " Por favor, ingrésela nuevamente.");
                                    }
                                }
                                ingresoValido=false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el número de teléfono del cliente: ");
                                        numeroTelefonoCliente = scanner.nextLine();

                                        // Comprobar si el número de teléfono contiene solo números
                                        Long.parseLong(numeroTelefonoCliente);
                                        ingresoValido = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("El número de teléfono debe contener solo números. Por favor, ingréselo nuevamente.");
                                    }
                                }
                                ingresoValido=false;
                                Cliente nuevoCliente = new Cliente(nombreCliente, apellidoCliente, dniCliente, emailCliente, direccionCliente, numeroTelefonoCliente);
                                // Cargar las mascotas del cliente
                                boolean cargarMascotas = true;
                                while (cargarMascotas) {
                                    do {
                                        try {
                                            System.out.print("Ingrese el nombre de la mascota (o 'salir' para finalizar la carga): ");
                                            nombreMascota = scanner.nextLine();
                                            if (nombreMascota.matches(".*\\d.*")) {
                                                throw new Exception("No se permiten números en el nombre de la mascota.");
                                            }
                                            ingresoValido = true;
                                        } catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());

                                        }
                                    }while (nombreMascota.matches(".*\\d.*"));
                                    if (nombreMascota.equalsIgnoreCase("salir")) {
                                        cargarMascotas = false;
                                    } else {
                                        ingresoValido=false;
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese la especie de la mascota: ");
                                                especie = scanner.nextLine();

                                                if (!especie.equalsIgnoreCase("perro") && !especie.equalsIgnoreCase("gato")) {
                                                    throw new Exception("Debe ingresar solo 'perro' o 'gato'.");
                                                }

                                                ingresoValido = true;
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());

                                            }
                                        }
                                        ingresoValido=false;

                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese la fecha de nacimiento de la mascota (formato: Día/Mes/Año): ");
                                                fechaNacimientoStr = scanner.nextLine();
                                                fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                                ingresoValido = true;
                                            } catch (Exception e) {
                                                System.out.println("Error: La fecha no cumple con el formato válido (Día/Mes/Año). Inténtelo nuevamente.");
                                            }
                                        }
                                        ingresoValido=false;

                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el sexo de la mascota: ");
                                                sexo = scanner.nextLine().toLowerCase();

                                                if (!sexo.equals("macho") && !sexo.equals("hembra")) {
                                                    throw new Exception("Debe ingresar solo 'macho' o 'hembra'.");
                                                }
                                                ingresoValido = true;
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());
                                            }
                                        }
                                        ingresoValido=false;

                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el peso de la mascota: ");
                                                String pesoStr = scanner.nextLine();
                                                peso = Double.parseDouble(pesoStr);
                                                if (peso < 0) {
                                                    throw new Exception("Debe ingresar un valor mayor o igual a 0.");
                                                }
                                                ingresoValido = true;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Error: Debe ingresar un valor numérico.");
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());
                                            }
                                        }
                                        ingresoValido = false;
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("¿Está castrada la mascota? (s/n): ");
                                                String castradoStr = scanner.nextLine().toLowerCase();

                                                if (castradoStr.equals("s")) {
                                                    castrado = true;
                                                } else if (castradoStr.equals("n")) {
                                                    castrado = false;
                                                } else {
                                                    throw new Exception("Debe ingresar solo 's' o 'n'.");
                                                }

                                                ingresoValido = true;
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());

                                            }
                                        }
                                        Animal nuevaMascota = null;
                                        if (especie.equalsIgnoreCase("gato")) {
                                            System.out.println("Seleccione la raza del gato:");
                                            int contadorGato = 1;
                                            for (RazaGato raza : RazaGato.values()) {
                                                System.out.println(contadorGato + ". " + raza);
                                                contadorGato++;
                                            }
                                            int opcionRazaGato = scanner.nextInt();
                                            scanner.nextLine();
                                            RazaGato razaGato = RazaGato.values()[opcionRazaGato - 1];
                                            nuevaMascota = new Gato("", nombreMascota, fechaNacimiento, sexo, peso, castrado, razaGato);
                                        } else if (especie.equalsIgnoreCase("perro")) {
                                            System.out.println("Seleccione la raza del perro:");
                                            int contadorPerro = 1;
                                            for (RazaPerro raza : RazaPerro.values()) {
                                                System.out.println(contadorPerro + ". " + raza);
                                                contadorPerro++;
                                            }
                                            int opcionRazaPerro = scanner.nextInt();
                                            scanner.nextLine();
                                            RazaPerro razaPerro = RazaPerro.values()[opcionRazaPerro - 1];
                                            nuevaMascota = new Perro("", nombreMascota, fechaNacimiento, sexo, peso, castrado, razaPerro);
                                        }

                                        if (nuevaMascota != null) {
                                            nuevoCliente.agregarAnimal(nuevaMascota);
                                        }
                                    }
                                }
                                try {
                                    veterinaria.agregarCliente(nuevoCliente);
                                    System.out.println("Cliente agregado correctamente.");
                                } catch (Exception e) {
                                    System.out.println("Error al agregar cliente: " + e.getMessage());
                                }
                                break;
                            case 2:
                                // Eliminar cliente
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try{
                                    System.out.print("Ingrese el DNI del cliente a eliminar: ");
                                    dniCliente = scanner.nextLine();
                                    if (!veterinaria.getClientes().containsKey(dniCliente)){
                                        throw new Exception("El DNI no corresponde a ningun cliente");
                                    }
                                    veterinaria.eliminarCliente(dniCliente);
                                    System.out.println("Cliente eliminado correctamente.");
                                    ingresoValido=true;
                                    }catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                }
                            case 3:
                                // Modificar cliente
                                clienteExistente=null;
                                ingresoValido = false;
                                opcionModificar=0;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el DNI del cliente a modificar: ");
                                        dniCliente = scanner.nextLine();
                                        if (!veterinaria.getClientes().containsKey(dniCliente)){
                                            throw new Exception("El DNI no corresponde a ningun cliente");
                                        }
                                        clienteExistente= veterinaria.getCliente(dniCliente);
                                        ingresoValido=true;
                                    }catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }
                                }
                                ingresoValido=false;
                                if (clienteExistente == null) {
                                    System.out.println("El cliente no existe.");
                                    break;
                                }
                                System.out.println("Seleccione el parámetro a modificar:");
                                System.out.println("1. Nombre");
                                System.out.println("2. Apellido");
                                System.out.println("3. Email");
                                System.out.println("4. Dirección");
                                System.out.println("5. Número de teléfono");
                                System.out.println("6. Agregar mascota");
                                System.out.println("7. Quitar mascota");
                                while (!ingresoValido) {
                                    try {
                                        opcionModificar = scanner.nextInt();
                                        scanner.nextLine();
                                        ingresoValido=true;
                                    } catch (Exception e) {
                                        System.out.println("Debe ingresar el numero de alguna opcion.");
                                    }
                                }
                                ingresoValido=false;
                                switch (opcionModificar) {
                                    case 1:
                                    ingresoValido = false;
                                    String nuevoNombre;
                                    while (!ingresoValido) {
                                        try {
                                            System.out.print("Ingrese el nuevo nombre del cliente: ");
                                            nuevoNombre = scanner.nextLine();
                                            if (!nuevoNombre.matches("[a-zA-Z]+")) {
                                                throw new Exception("Debe ingresar solo letras.");
                                            }
                                            ingresoValido = true;
                                            clienteExistente.setNombre(nuevoNombre);
                                        } catch (Exception e) {
                                            System.out.println("Error: " + e.getMessage());

                                        }
                                    }
                                    break;
                                    case 2:
                                        ingresoValido = false;
                                        String nuevoApellido;
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el nuevo nombre del cliente: ");
                                                nuevoApellido = scanner.nextLine();
                                                if (!nuevoApellido.matches("[a-zA-Z]+")) {
                                                    throw new Exception("Debe ingresar solo letras.");
                                                }
                                                ingresoValido = true;
                                                clienteExistente.setApellido(nuevoApellido);
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());

                                            }
                                        }
                                        break;
                                    case 3:
                                        String nuevoEmail;
                                        ingresoValido = false;
                                        while(!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el nuevo email del cliente: ");
                                                nuevoEmail = scanner.nextLine();
                                                if (!nuevoEmail.contains("@") || !nuevoEmail.endsWith(".com")) {
                                                    throw new Exception("El email debe contener '@' y terminar con '.com'.");
                                                }
                                                ingresoValido = true;
                                                clienteExistente.setEmail(nuevoEmail);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage() + " Por favor, ingréselo nuevamente.");
                                            }
                                        }
                                        break;
                                    case 4:
                                        ingresoValido =false;
                                        String nuevoDireccion;
                                        while(!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el nuevo dirección del cliente: ");
                                                nuevoDireccion = scanner.nextLine();
                                                if (!nuevoDireccion.matches("[a-zA-Z0-9 ]+")) {
                                                    throw new Exception("La dirección debe contener solo letras y números.");
                                                }
                                                ingresoValido = true;
                                                clienteExistente.setDireccion(nuevoDireccion);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage() + " Por favor, ingrésela nuevamente.");
                                            }
                                        }

                                        break;
                                    case 5:
                                        ingresoValido = false;
                                        String nuevoNumeroTelefono;
                                        while(!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el nuevo número de teléfono del cliente: ");
                                                nuevoNumeroTelefono = scanner.nextLine();
                                                if (!nuevoNumeroTelefono.matches("[0-9]+")) {
                                                    throw new Exception("El número de teléfono debe contener solo números.");
                                                }
                                                ingresoValido = true;
                                                clienteExistente.setNumeroTelefono(nuevoNumeroTelefono);
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage() + " Por favor, ingrésela nuevamente.");
                                            }
                                        }
                                        break;
                                    case 6:
                                        // Agregar mascota
                                        ingresoValido=false;
                                        nombreMascota="";
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese el nombre de la mascota (o 'salir' para finalizar la carga): ");
                                                nombreMascota = scanner.nextLine();
                                                if (nombreMascota.matches(".*\\d.*")) {
                                                    throw new Exception("No se permiten números en el nombre de la mascota.");
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());

                                            }
                                        }
                                        ingresoValido=false;
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Ingrese la especie de la mascota: ");
                                                especie = scanner.nextLine();
                                                if (!especie.equalsIgnoreCase("perro") && !especie.equalsIgnoreCase("gato")) {
                                                    throw new Exception("Debe ingresar solo 'perro' o 'gato'.");
                                                }
                                                ingresoValido = true;
                                            } catch (Exception e) {
                                                System.out.println("Error: " + e.getMessage());

                                            }
                                        }
                                        Animal nuevaMascota = null;
                                        sexo="";peso=0d;castrado=null;especie="";
                                        fechaNacimiento=null;
                                        if (especie.equalsIgnoreCase("perro")) {
                                            // Crear perro
                                            ingresoValido=false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("Ingrese la fecha de nacimiento de la mascota (formato: Día/Mes/Año): ");
                                                    fechaNacimientoStr = scanner.nextLine();
                                                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                                    ingresoValido = true;
                                                } catch (Exception e) {
                                                    System.out.println("Error: La fecha no cumple con el formato válido (Día/Mes/Año). Inténtelo nuevamente.");
                                                }
                                            }
                                            ingresoValido = false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("Ingrese el sexo de la mascota: ");
                                                    sexo = scanner.nextLine().toLowerCase();

                                                    if (!sexo.equals("macho") && !sexo.equals("hembra")) {
                                                        throw new Exception("Debe ingresar solo 'macho' o 'hembra'.");
                                                    }
                                                    ingresoValido = true;
                                                } catch (Exception e) {
                                                    System.out.println("Error: " + e.getMessage());
                                                }
                                            }
                                            ingresoValido = false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("Ingrese el peso de la mascota: ");
                                                    String pesoStr = scanner.nextLine();
                                                    peso = Double.parseDouble(pesoStr);
                                                    if (peso < 0) {
                                                        throw new Exception("Debe ingresar un valor mayor o igual a 0.");
                                                    }
                                                    ingresoValido = true;
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Error: Debe ingresar un valor numérico.");
                                                } catch (Exception e) {
                                                    System.out.println("Error: " + e.getMessage());
                                                }
                                            }
                                            ingresoValido = false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("¿Está castrada la mascota? (s/n): ");
                                                    String castradoStr = scanner.nextLine().toLowerCase();
                                                    if (castradoStr.equals("s")) {
                                                        castrado = true;
                                                    } else if (castradoStr.equals("n")) {
                                                        castrado = false;
                                                    } else {
                                                        throw new Exception("Debe ingresar solo 's' o 'n'.");
                                                    }
                                                    ingresoValido = true;
                                                } catch (Exception e) {
                                                    System.out.println("Error: " + e.getMessage());
                                                }
                                            }
                                            System.out.print("Ingrese la raza de la mascota: ");
                                            String razaMascota = scanner.nextLine();
                                            // Crear la nueva mascota
                                            RazaPerro razaPerro = RazaPerro.valueOf(razaMascota.toUpperCase());
                                            nuevaMascota = new Perro("", nombreMascota, fechaNacimiento, sexo, peso, castrado, razaPerro);
                                        } else if (especie.equalsIgnoreCase("gato")) {
                                            // Crear gato
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("Ingrese la fecha de nacimiento de la mascota (formato: Día/Mes/Año): ");
                                                    fechaNacimientoStr = scanner.nextLine();
                                                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                                    ingresoValido = true;
                                                } catch (Exception e) {
                                                    System.out.println("Error: La fecha no cumple con el formato válido (Día/Mes/Año). Inténtelo nuevamente.");
                                                }
                                            }
                                            ingresoValido = false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("Ingrese el sexo de la mascota: ");
                                                    sexo = scanner.nextLine().toLowerCase();

                                                    if (!sexo.equals("macho") && !sexo.equals("hembra")) {
                                                        throw new Exception("Debe ingresar solo 'macho' o 'hembra'.");
                                                    }
                                                    ingresoValido = true;
                                                } catch (Exception e) {
                                                    System.out.println("Error: " + e.getMessage());
                                                }
                                            }
                                            ingresoValido = false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("Ingrese el peso de la mascota: ");
                                                    String pesoStr = scanner.nextLine();
                                                    peso = Double.parseDouble(pesoStr);
                                                    if (peso < 0) {
                                                        throw new Exception("Debe ingresar un valor mayor o igual a 0.");
                                                    }
                                                    ingresoValido = true;
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Error: Debe ingresar un valor numérico.");
                                                } catch (Exception e) {
                                                    System.out.println("Error: " + e.getMessage());
                                                }
                                            }
                                            ingresoValido = false;
                                            while (!ingresoValido) {
                                                try {
                                                    System.out.print("¿Está castrada la mascota? (s/n): ");
                                                    String castradoStr = scanner.nextLine().toLowerCase();
                                                    if (castradoStr.equals("s")) {
                                                        castrado = true;
                                                    } else if (castradoStr.equals("n")) {
                                                        castrado = false;
                                                    } else {
                                                        throw new Exception("Debe ingresar solo 's' o 'n'.");
                                                    }
                                                    ingresoValido = true;
                                                } catch (Exception e) {
                                                    System.out.println("Error: " + e.getMessage());
                                                }
                                            }
                                            System.out.print("Ingrese la raza de la mascota: ");
                                            String razaMascota = scanner.nextLine();
                                            // Crear la nueva mascota
                                            RazaGato razaGato = RazaGato.valueOf(razaMascota.toUpperCase());
                                            nuevaMascota = new Gato("", nombreMascota, fechaNacimiento, sexo, peso, castrado, razaGato);
                                        }

                                        if (nuevaMascota != null) {
                                            clienteExistente.agregarAnimal(nuevaMascota);
                                            System.out.println("Mascota agregada correctamente.");
                                        } else {
                                            System.out.println("Especie de mascota inválida.");
                                        }
                                        break;
                                    case 7:
                                        // Quitar mascota
                                        ingresoValido = false;
                                        while(!ingresoValido){
                                            try {
                                                System.out.print("Ingrese el nombre de la mascota a quitar: ");
                                                String nombreMascotaQuitar = scanner.nextLine();
                                                if (veterinaria.buscaMascota(clienteExistente, nombreMascotaQuitar)){
                                                    throw new Exception("El cliente no tiene una mascota con ese nombre");
                                                }
                                                clienteExistente.quitarAnimal(nombreMascotaQuitar);
                                                ingresoValido=true;
                                            }catch (Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        System.out.println("Mascota quitada correctamente.");
                                        break;
                                    default:
                                        System.out.println("Opción inválida.");
                                        break;
                                }
                                break;
                            case 4:
                                // Volver al menú principal
                                salirClientes = true;
                                break;

                            default:
                                System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                        }
                    }
                    break;

                case 2:
                    String nombreEmpleado="",apellidoEmpleado="",dniEmpleado="",emailEmpleado="",direccionEmpleado="",numeroTelefonoEmpleado="",fechaIngreso="";

                    // Submenú de Empleados
                    boolean salirEmpleados = false;
                    while (!salirEmpleados) {
                        System.out.println("------------------------------- Menú Empleados ---------------------------");
                        System.out.println("1. Agregar empleado");
                        System.out.println("2. Eliminar empleado");
                        System.out.println("3. Modificar empleado");
                        System.out.println("4. Pagar sueldo");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Ingrese la opción deseada: ");
                        int opcionEmpleados = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionEmpleados) {
                            case 1:
                                // Agregar empleado

                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el nombre del empleado: ");
                                        nombreEmpleado = scanner.nextLine();

                                        if (!nombreEmpleado.matches("[a-zA-Z]+")) {
                                            throw new Exception("Debe ingresar solo letras.");
                                        }

                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                System.out.print("Ingrese el apellido del empleado: ");
                                apellidoEmpleado = scanner.nextLine();
                                        if (!apellidoEmpleado.matches("[a-zA-Z]+")) {
                                            throw new Exception("Debe ingresar solo letras.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el DNI del empleado: ");
                                        dniEmpleado = scanner.nextLine();

                                        if (!dniEmpleado.matches("\\d+")) {
                                            throw new Exception("Debe ingresar solo números.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());

                                    }
                                }
                                ingresoValido = false;
                                while(!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el email del empleado: ");
                                        emailEmpleado = scanner.nextLine();
                                        if (!emailEmpleado.contains("@") || !emailEmpleado.endsWith(".com")) {
                                            throw new Exception("El email debe contener '@' y terminar con '.com'.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage() + " Por favor, ingréselo nuevamente.");
                                    }
                                }
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese la dirección del empleado: ");
                                        direccionEmpleado = scanner.nextLine();

                                        if (!direccionEmpleado.matches("[a-zA-Z0-9]+")) {
                                            throw new Exception("La dirección contiene caracteres no válidos.");
                                        }

                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el número de teléfono del empleado: ");
                                        numeroTelefonoEmpleado = scanner.nextLine();

                                        if (!numeroTelefonoEmpleado.matches("[0-9]+")) {
                                            throw new Exception("El número de teléfono contiene caracteres no válidos.");
                                        }

                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());

                                    }
                                }
                                Integer tipoEmpleado=0;
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.println("Seleccione el tipo de empleado:");
                                        System.out.println("1. Veterinario");
                                        System.out.println("2. Cajero");
                                        tipoEmpleado = scanner.nextInt();
                                        scanner.nextLine();

                                        if (tipoEmpleado != 1 && tipoEmpleado != 2) {
                                            throw new Exception("Valor no válido. Seleccione 1 o 2.");
                                        }

                                        ingresoValido = true;
                                    } catch (InputMismatchException e) {
                                        System.out.println("Error: Debe ingresar un número entero.");
                                        scanner.nextLine();
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                Empleado nuevoEmpleado = null;
                                switch (tipoEmpleado) {
                                    case 1:
                                        // Crear veterinario
                                        ingresoValido = false;
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Fecha de ingreso (formato: día/mes/año): ");
                                                fechaIngreso = scanner.nextLine();

                                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                                                fechaIngreso = LocalDate.parse(fechaIngreso, formatter).toString();

                                                ingresoValido = true;
                                            } catch (DateTimeParseException e) {
                                                System.out.println("Error: Formato de fecha inválido. Debe ser día/mes/año.");
                                            }
                                            catch (Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        List<String> serviciosHechos = new ArrayList<>();
                                        List<String> turnos = new ArrayList<>();
                                        boolean disponible = true;
                                        nuevoEmpleado = new Veterinario(nombreEmpleado, apellidoEmpleado, dniEmpleado, emailEmpleado, direccionEmpleado,
                                                numeroTelefonoEmpleado, fechaIngreso, serviciosHechos, turnos, disponible);
                                        break;
                                    case 2:
                                        // Crear cajero
                                        ingresoValido = false;
                                        while (!ingresoValido) {
                                            try {
                                                System.out.print("Fecha de ingreso (formato: día/mes/año): ");
                                                fechaIngreso = scanner.nextLine();

                                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                                                fechaIngreso = LocalDate.parse(fechaIngreso, formatter).toString();

                                                ingresoValido = true;
                                            } catch (DateTimeParseException e) {
                                                System.out.println("Error: Formato de fecha inválido. Debe ser día/mes/año.");
                                            }
                                            catch (Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                        int horasTrabajadas = 0;
                                        nuevoEmpleado = new Cajero(nombreEmpleado, apellidoEmpleado, dniEmpleado, emailEmpleado, direccionEmpleado,
                                                numeroTelefonoEmpleado, fechaIngreso, horasTrabajadas);
                                        break;
                                    default:
                                        System.out.println("Opción inválida. No se ha creado el empleado.");
                                }

                                if (nuevoEmpleado != null) {
                                    try {
                                        veterinaria.agregarEmpleado(nuevoEmpleado);
                                        System.out.println("Empleado agregado correctamente.");
                                    } catch (Exception e) {
                                        System.out.println("Error al agregar empleado: " + e.getMessage());
                                    }
                                }
                                break;

                            case 2:
                                // Eliminar empleado
                                ingresoValido=false;
                                while (!ingresoValido){
                                try {
                                System.out.print("Ingrese el DNI del empleado a eliminar: ");
                                String dniEliminarEmpleado = scanner.nextLine();
                                if (!veterinaria.existeEmpleado(dniEliminarEmpleado)){
                                    throw new Exception("No existe un empleado con ese DNI");
                                }
                                    veterinaria.eliminarEmpleado(dniEliminarEmpleado);
                                    System.out.println("Empleado eliminado correctamente.");
                                    ingresoValido=true;
                                } catch (Exception e) {
                                    System.out.println("Error al eliminar empleado: " + e.getMessage());
                                }
                                }
                                break;

                            case 3:
                                // Modificar empleado
                                ingresoValido=false;
                                while (!ingresoValido){
                                    try {
                                        System.out.print("Ingrese el DNI del empleado a modificar: ");
                                         dniEmpleado = scanner.nextLine();
                                        if (!veterinaria.existeEmpleado(dniEmpleado)){
                                            throw new Exception("No existe un empleado con ese DNI");
                                        }
                                        ingresoValido=true;
                                    } catch (Exception e) {
                                        System.out.println("Error al eliminar empleado: " + e.getMessage());
                                    }
                                }
                                Empleado empleadoModificar = veterinaria.getEmpleados().get(dniEmpleado);

                                if (empleadoModificar == null) {
                                    System.out.println("El empleado no existe.");
                                    break;
                                }

                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el nombre del empleado: ");
                                        nombreEmpleado = scanner.nextLine();

                                        if (!nombreEmpleado.matches("[a-zA-Z]+")) {
                                            throw new Exception("Debe ingresar solo letras.");
                                        }

                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }

                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el apellido del empleado: ");
                                        apellidoEmpleado = scanner.nextLine();
                                        if (!apellidoEmpleado.matches("[a-zA-Z]+")) {
                                            throw new Exception("Debe ingresar solo letras.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                ingresoValido = false;
                                while(!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el email del empleado: ");
                                        emailEmpleado = scanner.nextLine();
                                        if (!emailEmpleado.contains("@") || !emailEmpleado.endsWith(".com")) {
                                            throw new Exception("El email debe contener '@' y terminar con '.com'.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage() + " Por favor, ingréselo nuevamente.");
                                    }
                                }
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese la dirección del empleado: ");
                                        direccionEmpleado = scanner.nextLine();

                                        if (!direccionEmpleado.matches("[a-zA-Z0-9]+")) {
                                            throw new Exception("La dirección contiene caracteres no válidos.");
                                        }

                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                ingresoValido = false;
                                while (!ingresoValido) {
                                    try {
                                        System.out.print("Ingrese el número de teléfono del empleado: ");
                                        numeroTelefonoEmpleado = scanner.nextLine();

                                        if (!numeroTelefonoEmpleado.matches("[0-9]+")) {
                                            throw new Exception("El número de teléfono contiene caracteres no válidos.");
                                        }
                                        ingresoValido = true;
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());

                                    }
                                }


                                if (empleadoModificar instanceof Veterinario) {

                                    Veterinario veterinarioModificar = (Veterinario) empleadoModificar;
                                    System.out.print("Ingrese la nueva lista de servicios hechos (separados por coma): ");
                                    String nuevaListaServiciosHechos = scanner.nextLine();

                                    List<String> serviciosHechos = Arrays.asList(nuevaListaServiciosHechos.split(","));
                                    veterinarioModificar.setServiciosHechos(serviciosHechos);
                                    System.out.print("Ingrese la nueva lista de turnos (separados por coma): ");
                                    String nuevaListaTurnos = scanner.nextLine();

                                    List<String> turnos = Arrays.asList(nuevaListaTurnos.split(","));
                                    veterinarioModificar.setTurnos(turnos);
                                    System.out.print("Ingrese la disponibilidad del veterinario (true/false): ");
                                    boolean nuevaDisponibilidad = scanner.nextBoolean();
                                    veterinarioModificar.setDisponible(nuevaDisponibilidad);
                                }
                                else if (empleadoModificar instanceof Cajero) {

                                    Cajero cajeroModificar = (Cajero) empleadoModificar;
                                    System.out.print("Ingrese las nuevas horas trabajadas: ");
                                    int nuevasHorasTrabajadas = scanner.nextInt();
                                    cajeroModificar.setHorasTrabajadas(nuevasHorasTrabajadas);

                                }


                                empleadoModificar.setNombre(nombreEmpleado);
                                empleadoModificar.setApellido(apellidoEmpleado);
                                empleadoModificar.setEmail(emailEmpleado);
                                empleadoModificar.setDireccion(direccionEmpleado);
                                empleadoModificar.setNumeroTelefono(numeroTelefonoEmpleado);

                                System.out.println("Empleado modificado correctamente.");
                                break;
                            case 4 :
                                // Pagar sueldo
                                System.out.print("Ingrese el DNI del empleado: ");
                                String dniEmpleadoSueldo = scanner.nextLine();
                                Empleado empleadoSueldo = veterinaria.getEmpleado(dniEmpleadoSueldo);
                                if (empleadoSueldo == null) {
                                    System.out.println("El empleado no existe.");
                                    break;
                                }

                                if (empleadoSueldo instanceof Cajero) {
                                    Cajero cajeroSueldo = (Cajero) empleadoSueldo;
                                    cajeroSueldo.calcularYAsignarSueldo();
                                } else if (empleadoSueldo instanceof Veterinario) {
                                    Veterinario veterinarioSueldo = (Veterinario) empleadoSueldo;
                                    veterinarioSueldo.calcularYAsignarSueldo();
                                }
                                break;

                            case 5:
                                // Volver al menú principal
                                salirEmpleados = true;
                                break;

                            default:
                                System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                        }
                    }
                    break;

                case 3:
                    // Realizar servicio
                    String dniClienteServicio="",dniVeterinario="", servicio="";
                    ingresoValido=false;
                    while (!ingresoValido) {
                        try {
                            System.out.print("Ingrese el DNI del cliente: ");
                            dniClienteServicio = scanner.nextLine();

                            if (!dniClienteServicio.matches("[0-9]+")) {
                                throw new Exception("El DNI debe contener solo números.");
                            }
                            else if (veterinaria.getCliente(dniClienteServicio)==null) {
                                throw new Exception("El cliente no existe.");
                            }
                            ingresoValido = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    ingresoValido=false;
                    while (!ingresoValido) {
                        try {
                            System.out.print("Ingrese el DNI del veterinario: ");
                            dniVeterinario = scanner.nextLine();
                            if (!dniVeterinario.matches("[0-9]+")) {
                                throw new Exception("El DNI debe contener solo números.");
                            }
                            else if (veterinaria.getEmpleado(dniVeterinario)==null) {
                                throw new Exception("El veterinario no existe.");
                            }
                            ingresoValido=true;
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    ingresoValido=false;
                    while (!ingresoValido) {
                        try {
                            System.out.print("Ingrese el servicio a realizar (vacunar, castrar, consulta): ");
                            servicio = scanner.nextLine();
                            if (servicio.equalsIgnoreCase("vacunar") || servicio.equalsIgnoreCase("castrar") || servicio.equalsIgnoreCase("consulta")) {
                                ingresoValido = true;
                            } else {
                                throw new Exception("Error: Opción de servicio no válida. Por favor, ingrese vacunar, castrar o consulta.");
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    try {
                        veterinaria.realizarServicio(dniClienteServicio, dniVeterinario, servicio);
                        System.out.println("Servicio realizado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al realizar el servicio: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Ver información del sistema
                    String dniCliente="";
                    Cliente clienteInfo=null;
                    int opcionInformacion = 0;
                    do {
                        System.out.println("Seleccione la opción deseada:");
                        System.out.println("1. Información del cliente");
                        System.out.println("2. Información del empleado");
                        System.out.println("3. Información del veterinario");
                        System.out.print("Ingrese el número de opción: ");
                        opcionInformacion = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionInformacion < 1 || opcionInformacion > 3) {
                            System.out.println("Error: Opción no válida. Por favor, ingrese un número entre 1 y 3.");
                        }
                    } while (opcionInformacion < 1 || opcionInformacion > 3);
                    scanner.nextLine();
                    switch (opcionInformacion) {
                        case 1:
                            // Información del cliente

                            ingresoValido = false;
                            while (!ingresoValido) {
                                try {
                                System.out.print("Ingrese el DNI del cliente: ");
                                dniCliente = scanner.nextLine();
                                long dni = Long.parseLong(dniCliente);
                                ingresoValido = true;
                                if (veterinaria.getCliente(dniCliente)==null){
                                    throw new Exception("El cliente no existe");
                                }else {
                                    clienteInfo = veterinaria.getCliente(dniCliente);
                                }
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: El DNI debe contener solo números. Por favor, ingrese nuevamente.");
                                }
                                catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }

                            if (clienteInfo == null) {
                                System.out.println("El cliente no existe.");
                            } else {
                                System.out.println("Datos del cliente:");
                                System.out.println("Nombre: " + clienteInfo.getNombre());
                                System.out.println("Apellido: " + clienteInfo.getApellido());
                                System.out.println("DNI: " + clienteInfo.getDni());
                                System.out.println("Email: " + clienteInfo.getEmail());
                                System.out.println("Dirección: " + clienteInfo.getDireccion());
                                System.out.println("Número de teléfono: " + clienteInfo.getNumeroTelefono());
                            }
                            break;
                        case 2:
                            // Información del empleado
                            System.out.print("Ingrese el DNI del empleado: ");
                            String dniEmpleadoInfo = scanner.nextLine();

                            Empleado empleadoInfo = veterinaria.getEmpleado(dniEmpleadoInfo);
                            if (empleadoInfo == null) {
                                System.out.println("El empleado no existe.");
                            } else {
                                System.out.println("Datos del empleado:");
                                System.out.println("Nombre: " + empleadoInfo.getNombre());
                                System.out.println("Apellido: " + empleadoInfo.getApellido());
                                System.out.println("DNI: " + empleadoInfo.getDni());
                                System.out.println("Email: " + empleadoInfo.getEmail());
                                System.out.println("Dirección: " + empleadoInfo.getDireccion());
                                System.out.println("Número de teléfono: " + empleadoInfo.getNumeroTelefono());
                            }
                            break;

                        case 3:
                            // Información del veterinario
                            int opcionVeterinarioInfo = 0;
                            String dniVeterinarioInfo="";
                            do {
                                System.out.println("Seleccione la opción deseada:");
                                System.out.println("1. Ver turnos próximos del veterinario");
                                System.out.println("2. Ver servicios realizados por el veterinario");
                                System.out.print("Ingrese el número de opción: ");
                                opcionVeterinarioInfo = scanner.nextInt();
                                scanner.nextLine();
                                if (opcionVeterinarioInfo < 1 || opcionVeterinarioInfo > 2) {
                                    System.out.println("Error: Opción no válida. Por favor, ingrese un número entre 1 y 2.");
                                }
                            } while (opcionVeterinarioInfo < 1 || opcionVeterinarioInfo > 2);
                            scanner.nextLine();
                            ingresoValido=false;
                            while (!ingresoValido) {
                                try {
                                    System.out.print("Ingrese el DNI del veterinario: ");
                                    dniVeterinarioInfo = scanner.nextLine();
                                    if (veterinaria.getVeterinarioByDni(dniVeterinarioInfo)==null){
                                     throw new Exception("El DNI no corresponde a un veterinario");
                                    }
                                    ingresoValido=true;
                                }catch (Exception e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            Veterinario veterinarioInfo = veterinaria.getVeterinarioByDni(dniVeterinarioInfo);
                            if (veterinarioInfo == null) {
                                System.out.println("El veterinario no existe.");
                            } else {
                                switch (opcionVeterinarioInfo) {
                                    case 1:
                                        // Ver turnos próximos del veterinario
                                        List<String> turnosVeterinario = veterinarioInfo.getTurnos();
                                        if (turnosVeterinario.isEmpty()) {
                                            System.out.println("El veterinario no tiene turnos próximos.");
                                        } else {
                                            System.out.println("Turnos próximos del veterinario:");
                                            for (String turno : turnosVeterinario) {
                                                System.out.println(turno);
                                            }
                                        }
                                        break;

                                    case 2:
                                        // Ver servicios realizados por el veterinario
                                        List<String> serviciosVeterinario = veterinarioInfo.getServiciosHechos();
                                        if (serviciosVeterinario.isEmpty()) {
                                            System.out.println("El veterinario no ha realizado servicios.");
                                        } else {
                                            System.out.println("Servicios realizados por el veterinario:");
                                            for (String serv : serviciosVeterinario) {
                                                System.out.println(serv);
                                            }
                                        }
                                        break;

                                    default:
                                        System.out.println("Opción inválida.");
                                        break;
                                }
                            }
                            break;

                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }
                    break;

                case 5:
                    // Agendar turno
                    String dniVeterinarioTurno = "", fechaTurno = "", horaTurno = "";
                    Veterinario veterinarioTurno = null;
                     dniCliente = "";
                    ingresoValido = false;
                    while (!ingresoValido) {
                        try {
                            System.out.print("Ingrese el DNI del veterinario: ");
                            dniVeterinarioTurno = scanner.nextLine();
                            if (!dniVeterinarioTurno.matches("[0-9]+")) {
                                throw new Exception("El DNI debe contener solo números.");
                            } else if (veterinaria.getVeterinarioByDni(dniVeterinarioTurno) == null) {
                                throw new Exception("El veterinario no existe.");
                            }
                            ingresoValido = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }


                    veterinarioTurno = veterinaria.getVeterinarioByDni(dniVeterinarioTurno);

                    if (veterinarioTurno != null) {
                        ingresoValido = false;
                        while (!ingresoValido) {
                            try {
                                System.out.print("Ingrese la fecha del turno (formato: dd/MM/yyyy): ");
                                fechaTurno = scanner.nextLine();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate.parse(fechaTurno, formatter);
                                ingresoValido = true;
                            } catch (DateTimeParseException e) {
                                System.out.println("Error: Formato de fecha incorrecto. Por favor, ingrese nuevamente.");
                            }
                        }
                        ingresoValido = false;
                        while (!ingresoValido) {
                            System.out.print("Ingrese la hora del turno (formato HH:mm): ");
                            horaTurno = scanner.nextLine();
                            try {
                                LocalTime.parse(horaTurno);
                                ingresoValido = true;
                            } catch (DateTimeParseException e) {
                                System.out.println("Error: Formato de hora incorrecto. Por favor, ingrese nuevamente.");
                            }
                        }
                        System.out.print("Ingrese el nombre de la mascota: ");
                        String nombreMascotaTurno = scanner.nextLine();


                        String turno = "Fecha: " + fechaTurno + ", Hora: " + horaTurno + ", Mascota: " + nombreMascotaTurno;
                        veterinarioTurno.agendarTurno(turno);
                        System.out.println("Turno agendado correctamente.");


                        try {

                            List<String> turnosList = veterinarioTurno.getTurnos();


                            String turnosJson = objectMapper.writeValueAsString(turnosList);


                            Files.writeString(Paths.get("turnos.json"), turnosJson);
                            System.out.println("Turnos guardados en el archivo 'turnos.json'.");
                        } catch (IOException e) {
                            System.out.println("Error al guardar los turnos en el archivo JSON: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Veterinario no encontrado.");
                    }
                    break;
                case 6:

                    // Cobrar cliente
                    dniCliente="";
                    Cliente cliente=null;
                    ingresoValido = false;
                    while (!ingresoValido) {
                        try {
                        System.out.print("Ingrese el DNI del cliente: ");
                        dniCliente = scanner.nextLine();
                        long dni = Long.parseLong(dniCliente);
                        if (veterinaria.getCliente(dniCliente)==null){
                            throw new Exception("El cliente no existe");
                        }
                        ingresoValido = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: El DNI debe contener solo números. Por favor, ingrese nuevamente.");
                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }

                    cliente= veterinaria.getCliente(dniCliente);

                    if (cliente == null) {
                        System.out.println("El cliente no existe.");
                    } else {

                        ArrayList<Ticket> tickets = cliente.getTickets();


                        if (tickets.isEmpty()) {
                            System.out.println("El cliente no tiene tickets registrados.");
                        } else {

                            System.out.println("----- Tickets del cliente -----");
                            for (Ticket ticket : tickets) {
                                imprimirTicket(ticket);
                            }
                        }
                    }
                    break;
                case 7:
                    Listados datos=new Listados<>();
                    datos.carga(veterinaria.getClientes());
                    datos.carga(veterinaria.getEmpleados());
                    for (int i=0;i<datos.totalDatos();i++){

                        System.out.println(datos.getDato(i));
                    }

                    break;
                case 8:
                    // Salir del programa
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        }
        PersistenciaSistema.guardarVeterinaria(veterinaria);
    }
}