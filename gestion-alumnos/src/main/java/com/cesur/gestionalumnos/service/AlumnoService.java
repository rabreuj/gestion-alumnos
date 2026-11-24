package com.cesur.gestionalumnos.service;

import java.io.IOException;
import java.util.ArrayList;

import com.cesur.gestionalumnos.model.Alumno;

import com.cesur.gestionalumnos.repository.*;

public class AlumnoService {
    private AlumnoRepository repository;

    public AlumnoService (AlumnoRepository repository) {
        this.repository = repository;
    }

    public void registrarAlumno(String nombre, int edad, String ciclo) throws IOException {
        Alumno alumno = new Alumno(nombre, edad, ciclo);
        repository.guardar(alumno);
    }
    
    public ArrayList<Alumno> listarAlumno() throws IOException {
        return repository.obtenerTodos();
    }

    public ArrayList<Alumno> buscarPorNombre (String nombre) throws IOException {
        ArrayList<Alumno> encontrados = repository.buscarPorNombre(nombre);
        return encontrados;
    }

    public boolean eliminarAlumno(String nombre) throws IOException {
        return repository.eliminarPorNombre(nombre);
    }
}
