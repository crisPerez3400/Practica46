package TiendaVideojuegos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class GestorUsuarios {
    private static String archivo;
    private ArrayList<String> lineas; // se crea un arraylist
    Scanner scanner = new Scanner(System.in);

    public GestorUsuarios(String archivo) {
        GestorUsuarios.archivo = archivo;
        lineas = new ArrayList<String>();
        scanner = new Scanner(System.in);
        ; // carga los datos del txt al iniciarse

    }

    // cargar los datos en el arraylist
    private void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar los datos del archivo: " + e.getMessage());
        }
    }

    // VALIDA formato DNI
    public String validacion(String dni) {
        String patronDNI = "\\d{8}[A-Z]";
        Pattern pattern = Pattern.compile(patronDNI);
        Matcher matcher = pattern.matcher(dni);
        while (!matcher.matches()) {
            System.out.println("DNI inválido. Debe tener 8 dígitos seguidos de una letra mayúscula.");
            System.out.println("Introduzca DNI:");
            dni = scanner.nextLine();
            matcher = pattern.matcher(dni);
        }
        return dni; // Retorna el DNI validado
    }

    // Buscar datos usuario
    public boolean buscarDatos(String dniBuscar) {
        cargarDatos();
        dniBuscar = validacion(dniBuscar); // Usa el DNI validado
        for (String i : lineas) {
            if (i.contains(dniBuscar)) {
                System.out.println(i);
                return true;
            }
        }
        // System.out.println("El usuario no esta en lista");
        return false;
    }

    public void eliminarUsuario() {
        System.out.println("Introduzca DNI: ");
        String dniBorrar = scanner.nextLine();
        String dniValidado = validacion(dniBorrar); // Valida el dni
       // boolean encontrado = false;
        cargarDatos(); // se cargan los datos en el arraylist
        lineas.removeIf(linea -> linea.contains(dniValidado)); //borra la linea si contiene el dni

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (String i : lineas) {
                writer.write(i);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }


    }

    // Introducir datos
    public void introducirDatos() {
        System.out.println("Introduzca DNI a agregar: ");
        String dniBuscar = scanner.nextLine();
        String dniValidado = validacion(dniBuscar); // Valida el dni

        boolean dniEncontrado = buscarDatos(dniBuscar); // comprueba si el dni esta en lista
        if (!dniEncontrado) {
            System.out.println("Introduzca Nombre:");
            String nombre = scanner.nextLine();

            System.out.println("Introduzca apellidos:");
            String apellidos = scanner.nextLine();

            System.out.println("Introduzca número de teléfono:");
            String telefono = scanner.nextLine();

            System.out.println("Introduzca correo electrónico");
            String correo = scanner.nextLine();

            try (FileWriter fileWriter = new FileWriter(archivo, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter
                        .write(dniValidado + ", " + nombre + " " + apellidos + ", " + telefono + ", " + correo + "\n");
                System.out.println("Ha sido agregado correctamente");
            } catch (IOException e) {
                System.err.println("Error al agregar la línea: " + e.getMessage());
            }
        } else {
            System.out.println("El DNI ya se encuentra en la lista");
        }
    }

}
