Sistema de gestión de alumnos (versión 1)
Objetivos
Un centro educativo necesita una aplicación para registrar alumnos y consultar los datos almacenados.
Vamos a desarrollar una primera versión de esta aplicación utilizando archivos de texto para guardar la información.
Además de practicar BufferedReader, BufferedWriter y try-with-resources, aprenderemos a organizar una aplicación en tres capas, separando sus responsabilidades.
Trabajaremos con Path, Files, ArrayList, clases, objetos, métodos y excepciones.
Esta será la primera versión de un proyecto que ampliaremos en próximas clases.
1. Organización de la aplicación
Nuestra aplicación tendrá tres capas:
Capa	Clase	Responsabilidad
Presentación	Main	Mostrar el menú e interactuar con el usuario
Negocio	AlumnoService	Coordinar las operaciones sobre alumnos
Datos	AlumnoRepository	Guardar y recuperar alumnos del archivo
Además, utilizaremos una clase Alumno para representar los datos de cada estudiante.
Estructura del proyecto
GestionAlumnos/
├── Main.java
├── Alumno.java
├── AlumnoService.java
├── AlumnoRepository.java
└── datos/
    └── alumnos.txt
El archivo alumnos.txt se creará automáticamente cuando registremos el primer alumno.
Por ahora, todas las clases estarán en el mismo directorio y no utilizaremos paquetes.
Comunicación entre capas
        USUARIO
           |
           v
         Main
     (Presentación)
           |
           v
     AlumnoService
       (Negocio)
           |
           v
    AlumnoRepository
         (Datos)
           |
           v
       alumnos.txt
Regla importante: solamente AlumnoRepository puede leer o escribir archivos.
Main no debe utilizar BufferedReader ni BufferedWriter, y AlumnoService tampoco.
2. Crear la clase Alumno
La clase Alumno representará a un estudiante.
Requisitos
Crear una clase llamada Alumno.
Declarar tres atributos privados:
nombre (String).
edad (int).
ciclo (String).
Crear un constructor que inicialice los atributos.
Crear los getters correspondientes.
Sobrescribir toString() para mostrar los datos del alumno.
Ejemplo de utilización
Alumno alumno = new Alumno("Ana", 20, "DAM");
System.out.println(alumno);
Salida esperada:
Ana - 20 años - DAM
Importante: La clase Alumno no debe acceder a archivos ni solicitar información al usuario.
3. Crear la capa de datos — AlumnoRepository
Esta clase será responsable de guardar y recuperar los alumnos.
Los datos se almacenarán en:
datos/alumnos.txt
Cada alumno ocupará una línea con el siguiente formato:
Nombre;Edad;Ciclo
Ejemplo:
Ana;20;DAM
Pedro;22;DAW
Lucia;19;DAM
Estructura inicial
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
public class AlumnoRepository {
    private Path ruta;
    public AlumnoRepository(Path ruta) {
        this.ruta = ruta;
    }
    public void guardar(Alumno alumno) throws IOException {
        // Pendiente
    }
    public ArrayList<Alumno> obtenerTodos() throws IOException {
        // Pendiente
        return new ArrayList<>();
    }
}
Parte A — Implementar guardar()
El método recibirá un objeto Alumno y lo añadirá al archivo.
Requisitos:
Obtener el nombre, la edad y el ciclo mediante los getters.
Construir un String con el formato Nombre;Edad;Ciclo.
Crear el directorio datos si todavía no existe.
Abrir el archivo utilizando Files.newBufferedWriter().
Utilizar StandardOpenOption.CREATE y StandardOpenOption.APPEND.
Escribir el registro mediante write().
Añadir un salto de línea mediante newLine().
Utilizar try-with-resources para cerrar el escritor.
Pista:
String registro = alumno.getNombre() + ";"
        + alumno.getEdad() + ";"
        + alumno.getCiclo();
Si registramos dos alumnos, el archivo deberá contener:
Ana;20;DAM
Pedro;22;DAW
Los registros anteriores deben conservarse.
Parte B — Implementar obtenerTodos()
El método deberá leer el archivo y devolver una lista de objetos Alumno.
Requisitos:
Crear un ArrayList<Alumno> vacío.
Comprobar si existe el archivo mediante Files.exists().
Si no existe, devolver la lista vacía.
Abrir el archivo utilizando Files.newBufferedReader().
Leer las líneas mediante readLine() y un bucle while.
Separar los campos de cada línea utilizando split(";").
Convertir la edad a int mediante Integer.parseInt().
Crear un objeto Alumno con los datos obtenidos.
Añadir el objeto a la lista.
Devolver la lista al finalizar.
Pistas:
String[] campos = linea.split(";");
String nombre = campos[0];
int edad = Integer.parseInt(campos[1]);
String ciclo = campos[2];
Alumno alumno = new Alumno(nombre, edad, ciclo);
Por ahora, suponemos que todos los registros tienen el formato correcto.
Importante: obtenerTodos() devuelve objetos Alumno, no cadenas de texto.
4. Crear la capa de negocio — AlumnoService
Esta clase se encargará de coordinar las operaciones de la aplicación.
No necesita conocer cómo se almacenan los alumnos. Simplemente utilizará los métodos del repositorio.
Estructura inicial
import java.io.IOException;
import java.util.ArrayList;
public class AlumnoService {
    private AlumnoRepository repository;
    public AlumnoService(AlumnoRepository repository) {
        this.repository = repository;
    }
    public void registrarAlumno(
            String nombre, int edad, String ciclo)
            throws IOException {
        // Pendiente
    }
    public ArrayList<Alumno> listarAlumnos()
            throws IOException {
        // Pendiente
        return new ArrayList<>();
    }
}
Parte A — Implementar registrarAlumno()
El método deberá:
Recibir nombre, edad y ciclo como parámetros.
Crear un objeto Alumno con esos datos.
Llamar al método guardar() del repositorio.
Pista:
Alumno alumno = new Alumno(nombre, edad, ciclo);
Parte B — Implementar listarAlumnos()
El método deberá:
Llamar a repository.obtenerTodos().
Devolver la lista obtenida.
Importante: AlumnoService no debe utilizar Scanner, BufferedReader ni BufferedWriter.
5. Crear la capa de presentación — Main
La clase Main será responsable de mostrar el menú y comunicarse con el usuario.
Preparación
En el método main(), crea los objetos necesarios:
Path ruta = Path.of("datos", "alumnos.txt");
AlumnoRepository repository =
        new AlumnoRepository(ruta);
AlumnoService service =
        new AlumnoService(repository);
A partir de este momento, Main deberá utilizar los métodos de AlumnoService para trabajar con los alumnos.
Menú
===== GESTION DE ALUMNOS =====
1. Registrar alumno
2. Mostrar alumnos
3. Salir
Selecciona una opcion:
Requisitos
Utilizar Scanner para leer las opciones.
Mantener el menú mediante un bucle while o do-while.
Utilizar switch para gestionar las opciones.
Mostrar un mensaje cuando la opción sea incorrecta.
Finalizar cuando el usuario seleccione la opción 3.
Opción 1 — Registrar alumno
Solicitar nombre, edad y ciclo.
Guardar los datos en variables.
Llamar a service.registrarAlumno().
Mostrar un mensaje confirmando el registro.
Capturar las posibles excepciones.
Ejemplo:
Nombre: Ana
Edad: 20
Ciclo: DAM
Alumno registrado correctamente.
Opción 2 — Mostrar alumnos
Llamar a service.listarAlumnos().
Recibir el ArrayList<Alumno>.
Comprobar si está vacío.
Si está vacío, mostrar un mensaje.
Si contiene alumnos, recorrerlo mediante un for-each.
Mostrar cada alumno utilizando su método toString().
Ejemplo:
===== ALUMNOS REGISTRADOS =====
Ana - 20 años - DAM
Pedro - 22 años - DAW
Lucia - 19 años - DAM
Gestión de errores
Utiliza try-catch en Main para mostrar un mensaje comprensible cuando se produzca una IOException.
Para simplificar esta primera versión, suponemos que el usuario introduce una edad numérica válida.
6. Comprobar el funcionamiento
Una vez terminada la aplicación:
Ejecutar el programa.
Registrar tres alumnos.
Mostrar el listado y comprobar que aparecen los tres.
Salir del programa.
Ejecutarlo nuevamente.
Mostrar los alumnos sin registrar ninguno nuevo.
Comprobar que los registros anteriores siguen almacenados.
Registrar un cuarto alumno.
Comprobar que aparecen los cuatro.
Resultado esperado
GestionAlumnos/
├── Main.java
├── Alumno.java
├── AlumnoService.java
├── AlumnoRepository.java
└── datos/
    └── alumnos.txt
Contenido de alumnos.txt:
Ana;20;DAM
Pedro;22;DAW
Lucia;19;DAM
Carlos;25;ASIR
7. Preguntas para responder
¿Qué responsabilidad tiene cada una de las tres capas?
¿Por qué Main no utiliza directamente BufferedWriter?
¿Qué ventaja tiene devolver un ArrayList<Alumno> en lugar de un ArrayList<String>?
¿Por qué utilizamos APPEND al registrar alumnos?
¿Qué ocurre si ejecutamos la aplicación varias veces?
¿Qué clase modificaríamos si quisiéramos guardar los alumnos en otro formato?
¿Qué tendríamos que cambiar si quisiéramos utilizar una base de datos en lugar de un archivo?
Restricciones
Utilizar Java 21.
Mantener las cuatro clases indicadas.
Utilizar BufferedReader y BufferedWriter para acceder al archivo.
Utilizar try-with-resources.
Utilizar ArrayList<Alumno> para recuperar los registros.
No utilizar Files.readAllLines(), Files.readString() ni Files.writeString().
No utilizar Streams ni expresiones lambda.
No utilizar Scanner fuera de Main.
No acceder a archivos desde Main ni desde AlumnoService.
No mostrar mensajes por consola desde AlumnoRepository ni desde AlumnoService.
Objetivo final: desarrollar una aplicación que permita registrar y consultar alumnos, manteniendo separadas las responsabilidades de presentación, negocio y datos.
Esta será nuestra versión 1.0. Conservaremos el proyecto para ampliarlo en próximas clases.
 