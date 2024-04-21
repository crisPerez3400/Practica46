package TiendaVideojuegos;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GestorUsuarios usuarios = new GestorUsuarios("Usuarios\\ListaUsuarios.txt");
        Videojuegos gestor = new Videojuegos("Juegos\\juegos.txt");

        while (true) {
            System.out.println("Para acceder a los usuarios introduzca 1, para los videojuegos 2");
            int seleccionMenu;
            int seleccion;
            try {
                seleccionMenu = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer después de leer el entero
                switch (seleccionMenu) {
                    case 1:
                        System.out.println(
                                "Introduzca 1 para buscar Usuario\n" + "2 para Introducir Usuario" + "3 para borrar");
                        seleccion = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer después de leer el entero
                        switch (seleccion) {
                            case 1:
                                System.out.println("Introduzca el DNI del usuario que quiere buscar");
                                String buscarDni = scanner.nextLine();
                                usuarios.buscarDatos(buscarDni);
                                break;
                            case 2:
                                usuarios.introducirDatos();
                                break;
                            case 3:
                                usuarios.eliminarUsuario();
                                break;
                            default:
                                System.out.println("Selección no válida.");

                        }
                        break;
                    case 2:
                        System.out.println("Introduzca 1 para buscar Juego\n" + "2 para Introducir Juego\n" + "3 para Registrar una compra");
                        seleccion = scanner.nextInt();
                        scanner.nextLine(); // Limpiar el buffer después de leer el entero
                        switch (seleccion) {
                            case 1:
                                System.out.println("Introduzca nombre del juego");
                                String juegoValidar = scanner.nextLine();
                                gestor.buscarJuego(juegoValidar);
                                break;
                            case 2:
                                gestor.introducirJuego();
                                break;
                            case 3:
                                gestor.comprarJuegos();
                              break;

                            default:
                                System.out.println("Selección no válida.");

                        }
                        break;
                    default:
                        System.out.println("Selección no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe introducir un número entero.");
                scanner.next(); // Limpiar el buffer en caso de excepción
            }

        }
    }
}
