package com.cesur.gestionalumnos;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import com.cesur.gestionalumnos.model.Alumno;
import com.cesur.gestionalumnos.repository.AlumnoRepository;
import com.cesur.gestionalumnos.service.AlumnoService;


public class Main {
    public static void main(String[] args) {
        Path ruta = Path.of("datos", "alumnos.txt");

        Scanner sc = new Scanner(System.in);
        AlumnoRepository repository = new AlumnoRepository(ruta);
        AlumnoService service = new AlumnoService(repository);
        
        int option = -1, edad = -1;
        String nombre = null, ciclo = null;

        do {
            System.out.println("===== GESTION DE ALUMNOS =====");
            System.out.println("1. Registrar alumno");
            System.out.println("2. Mostrar alumnos");
            System.out.println("3. Buscar alumno");
            System.out.println("4. Eliminar alumno");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opcion: ");

            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("==============================");
                System.out.println("Opción inválida");
                sc.nextLine();
                continue;
            }

            switch (option) {
                case 1: 
                    try {
                        System.out.print("Introduce el nombre: ");
                        if (!sc.hasNextInt() || !sc.hasNextDouble()) {
                            nombre = sc.nextLine();
                        } else {
                            System.out.println("==============================");
                            System.out.println("Valor inválido");
                            continue;
                        }
                        
                        System.out.print("Introduce la edad: ");
                        edad = sc.nextInt();

                        sc.nextLine();
                        System.out.print("Introduce el ciclo: ");
                        if (!sc.hasNextInt() || !sc.hasNextDouble()) {
                            ciclo = sc.nextLine();
                        } else {
                            System.out.println("==============================");
                            System.out.println("Valor inválido");
                            continue;
                        }
                        
                    } catch (Exception e) {
                        System.out.println("==============================");
                        System.out.println("Valor inválido");
                        sc.nextLine();
                        continue;
                    }

                    try {
                        service.registrarAlumno(nombre, edad, ciclo);
                        System.out.println("El alumno se registro correctamente!");
                    } catch (IOException e) {
                        System.out.println("==============================");
                        System.out.println("Algo falló! El alumno no se registro.");
                    }
                    
                    break;

                case 2:
                    try {
                        ArrayList<Alumno> alumnos = service.listarAlumno();

                        if (alumnos.isEmpty()) {
                            System.out.println("==============================");
                            System.out.println("No hay alumnos registrados.");
                        } else {
                            System.out.println("===== ALUMNOS REGISTRADOS =====");
                            for (Alumno alumno : alumnos) {
                                System.out.println(alumno);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("==============================");
                        System.out.println("Algo falló! No se muestran los alumnos");
                    }

                    break;

                case 3: 
                    buscarAlumno(sc, service);
                    break;
                
                case 4:
                    eliminarAlumno(sc, service);
                    break;

                case 5:
                    break;
            }
        } while (option != 5);
        System.out.println("==============================");
        System.out.println("Saliendo del programa...");
    }

    private static void buscarAlumno(Scanner sc, AlumnoService service) {
        String nombre;
        
        System.out.print("Introduce un nombre a buscar: ");
        
        if (!sc.hasNextInt() || !sc.hasNextDouble()) {
            nombre = sc.nextLine();
        } else {
            System.out.println("==============================");
            System.out.println("Valor inválido");
            return;
        }

        try {
            ArrayList<Alumno> resultado = service.buscarPorNombre(nombre);

            if (resultado.isEmpty()) {
                System.out.println("==============================");
                System.out.println("No se encontró ningun alumno.");
            } else {
                System.out.println("==============================");
                for (Alumno alumno : resultado) {
                    System.out.println(alumno);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarAlumno(Scanner sc, AlumnoService service) {
        String nombre;
        
        System.out.print("Introduce el nombre de un alumno a eliminar: ");
        
        if (!sc.hasNextInt() || !sc.hasNextDouble()) {
            nombre = sc.nextLine();
        } else {
            System.out.println("==============================");
            System.out.println("Valor inválido");
            return;
        }

        try {
            boolean eliminado = service.eliminarAlumno(nombre);

            if (eliminado) {
                System.out.println("==============================");
                System.out.println("Alumno eliminado correctamente!");
            } else {
                System.out.println("==============================");
                System.out.println("No se encontró al alumno.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
