package TiendaVideojuegos;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.io.BufferedReader;

public class Videojuegos {
    private Scanner scanner = new Scanner(System.in);
    private static String archivo;
    private static String Estado = "Disponible";
    private final String[] tiposConsola = {"PlayStation", "Xbox", "Nintendo Switch"};

    public Videojuegos(String archivo) {
        Videojuegos.archivo = archivo;
    }

    // generador claves aleatorias
    public String generarSerial() {
        int longitud = 10;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.";
        StringBuilder clave = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            clave.append(caracteres.charAt(indice));
        }
        return clave.toString();
    }

    // buscar DAtos
    public boolean buscarJuego(String SerialJuego) {
        List<String> coincidencias = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(SerialJuego)) {
                    // System.out.println(linea);
                    // return true;
                    coincidencias.add(linea);
                }
            }
            for (String i : coincidencias) {
                System.out.println(i);

            }
            return true;

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return false;
    }

    // Introducir JUegos
    public void introducirJuego() {
        System.out.println("Introduzca nombre del videojuego ");
        String JuegoInsertar = scanner.nextLine();

        System.out.println("Elija el tipo de consola (introduzca 1, 2 o 3):");
        System.out.println(Arrays.toString(tiposConsola));
        int consola = scanner.nextInt();
        scanner.nextLine(); 
        
        try (FileWriter fileWriter = new FileWriter(archivo, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String serial = generarSerial();
            bufferedWriter.write(JuegoInsertar + ", " + tiposConsola[consola - 1] + " " + serial + " Estado: " + Estado + "\n");
            System.out.println("Juego añadido");
        } catch (IOException e) {
            System.err.println("Error al agregar la línea: " + e.getMessage());
        }
    }

    public void comprarJuegos() {
        System.out.println("Introduzca serial del videojuego ");
        String JuegoInsertar = scanner.nextLine();
        

        boolean juegoEncontrado = false; // Variable para verificar si se encontró el juego
        String lineaJuego = null;
        try {
            // Abre el primer archivo para lectura
            BufferedReader archivo = new BufferedReader(new FileReader("Juegos\\juegos.txt"));
            // Abre el segundo archivo para lectura
            BufferedReader archivo2 = new BufferedReader(new FileReader("Usuarios\\ListaUsuarios.txt"));
    
            // Abre un archivo de salida para escribir
            BufferedWriter archivoSalida = new BufferedWriter(new FileWriter("Juegos\\compras.txt", true));
    
            // Lee línea por línea del primer archivo y verifica si el juego está presente
            String linea;
            while ((linea = archivo.readLine()) != null) {
                if (linea.contains(JuegoInsertar)) {
                    lineaJuego = linea;
                   // archivoSalida.write(linea + ", ");
                    juegoEncontrado = true; // El juego fue encontrado
                    break;
                } 
            }
    
            if (!juegoEncontrado) {
                System.out.println("Videojuego no encontrado");
                archivo.close();
                archivo2.close();
                archivoSalida.close();
                return; // Salir del método si el juego no está presente
            }

            else {
                System.out.println("Introduzca dni del usuario ");
                String dni = scanner.nextLine();

                while ((linea = archivo2.readLine()) != null) {
                    if (linea.contains(dni)) {
                        archivoSalida.write(linea + " // comprado por " + lineaJuego);
                        archivoSalida.newLine();
                        break;
                    }
                }

            }
    
            // Lee línea por línea del segundo archivo y escribe en el archivo de salida

    
            // Cierra los archivos
            archivo.close();
            archivo2.close();
            archivoSalida.close();
    
            System.out.println("Se ha completado la compra.");
    
        } catch (IOException e) {
            System.out.println("Error al leer o escribir archivos: " + e.getMessage());
        }
    }
    

}



