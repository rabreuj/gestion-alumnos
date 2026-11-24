package com.cesur.gestionalumnos.repository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import com.cesur.gestionalumnos.model.Alumno;

public class AlumnoRepository {
    private Path ruta;

    public AlumnoRepository(Path ruta) {
        this.ruta = ruta;
    }

    public void guardar (Alumno alumno) throws IOException {
        String formatAlumno = 
        alumno.getNombre() + ";" +
        alumno.getEdad() + ";" +
        alumno.getCiclo();
        Files.createDirectories(ruta.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(ruta, 
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND)) {
            
            bw.write(formatAlumno);
            bw.newLine();
        }
    }

    public ArrayList<Alumno> obtenerTodos() throws IOException {
        ArrayList<Alumno> alumnos = new ArrayList<>();

        if (!Files.exists(ruta)) {
            return new ArrayList<>();
        }

        try (BufferedReader br = Files.newBufferedReader(ruta)) {

            String linea = br.readLine();
            while (linea != null) {

                String[] campos = linea.split(";");

                String nombre = campos[0];
                int edad = Integer.parseInt(campos[1]);
                String ciclo = campos[2];

                alumnos.add(new Alumno(nombre, edad, ciclo));

                linea = br.readLine();
            }
        }

        return alumnos;
    }

    public ArrayList<Alumno> buscarPorNombre(String nombre) throws IOException {
        ArrayList<Alumno> encontrados = new ArrayList<>();
        ArrayList<Alumno> alumnos = obtenerTodos();

        for (Alumno alumno : alumnos) {
            if (alumno.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(alumno);
            }
        }

        return encontrados;
    }

    public boolean eliminarPorNombre(String nombre) throws IOException {
        ArrayList<Alumno> alumnos = obtenerTodos();
        boolean encontrado = false;

        for (int i = 0; i < alumnos.size(); i++) {
            if (alumnos.get(i).getNombre().equalsIgnoreCase(nombre)) {
                alumnos.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return encontrado;
        } else {
            try (BufferedWriter bw = Files.newBufferedWriter(ruta, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING)) {
                
                    for (Alumno alumno : alumnos) {

                    String alumnoFormato = alumno.getNombre() + ";" + 
                    alumno.getEdad() + ";" + 
                    alumno.getCiclo();

                    bw.write(alumnoFormato);
                    bw.newLine();
                }
            }
            return encontrado;
        }
    }
}
