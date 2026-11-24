Sistema de gestión de alumnos (versión 1.1)
Objetivos
En la práctica anterior desarrollamos una aplicación que permite registrar alumnos y consultar los datos almacenados en un fichero de texto.
También aprendimos a organizar una aplicación en tres capas, separando las responsabilidades de presentación, negocio y acceso a datos.
En esta ampliación incorporaremos dos nuevas funcionalidades:
Buscar alumnos por nombre.
Eliminar alumnos del fichero.
Seguiremos trabajando con BufferedReader, BufferedWriter, Path, Files, ArrayList, clases, objetos, métodos y excepciones.
Además, aprenderemos a modificar información almacenada en un fichero de texto y a reutilizar los métodos que ya hemos desarrollado.
Importante: no debemos crear una aplicación nueva. Ampliaremos el proyecto de la versión 1.0.
1. Organización de la aplicación
Mantendremos las cuatro clases existentes:
GestionAlumnos/
├── Main.java
├── Alumno.java
├── AlumnoService.java
├── AlumnoRepository.java
└── datos/
    └── alumnos.txt
Responsabilidades de cada clase
Clase	Responsabilidad
Main	Mostrar el menú, solicitar información y presentar resultados
AlumnoService	Coordinar las operaciones sobre los alumnos
AlumnoRepository	Guardar, recuperar, buscar y eliminar alumnos del fichero
Alumno	Representar los datos de cada alumno
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
Regla importante: solamente AlumnoRepository puede leer o escribir ficheros.
Main no debe acceder directamente al repositorio.
AlumnoService no debe utilizar BufferedReader, BufferedWriter ni conocer cómo se almacenan los alumnos.
Métodos que incorporaremos
AlumnoRepository:
public ArrayList<Alumno> buscarPorNombre(String nombre)
        throws IOException {
    // Pendiente
}
public boolean eliminarPorNombre(String nombre)
        throws IOException {
    // Pendiente
}
AlumnoService:
public ArrayList<Alumno> buscarPorNombre(String nombre)
        throws IOException {
    // Pendiente
}
public boolean eliminarAlumno(String nombre)
        throws IOException {
    // Pendiente
}
La clase Alumno no necesita modificaciones.
PARTE 1 — Buscar alumnos por nombre
2. Implementar buscarPorNombre() en AlumnoRepository
Queremos que el usuario pueda introducir un nombre o parte de él y consultar los alumnos coincidentes.
Supongamos que nuestro fichero contiene:
Ana García;20;DAM
Pedro López;22;DAW
Mariana Pérez;19;DAM
Carlos Rodríguez;25;ASIR
Si buscamos:
ana
Deberemos encontrar:
Ana García - 20 años - DAM
Mariana Pérez - 19 años - DAM
La búsqueda no distinguirá entre mayúsculas y minúsculas.
Estructura inicial
Añadir a AlumnoRepository:
public ArrayList<Alumno> buscarPorNombre(String nombre)
        throws IOException {
    // Pendiente
}
Requisitos
El método deberá:
Crear un ArrayList<Alumno> vacío para almacenar los resultados.
Recuperar todos los alumnos utilizando el método obtenerTodos().
Recorrer la lista mediante un bucle for-each.
Obtener el nombre de cada alumno mediante su getter.
Comprobar si contiene el texto recibido como parámetro.
Ignorar las diferencias entre mayúsculas y minúsculas.
Añadir los alumnos coincidentes a la lista de resultados.
Devolver la lista al finalizar.
Pistas
Para comprobar si una cadena contiene otra utilizamos contains():
String nombre = "Ana García";
boolean coincide = nombre.contains("Ana");
Sin embargo, contains() distingue entre mayúsculas y minúsculas.
Podemos solucionarlo convirtiendo ambas cadenas a minúsculas:
String nombre = "Ana García";
String buscado = "ana";
boolean coincide = nombre.toLowerCase()
        .contains(buscado.toLowerCase());
También podemos utilizar directamente el getter:
alumno.getNombre().toLowerCase()
        .contains(nombreBuscado.toLowerCase());
Importante: no necesitamos volver a implementar la lectura del fichero.
Ya tenemos un método que recupera todos los alumnos:
obtenerTodos();
Debemos reutilizarlo.
Resultado esperado
Si encontramos coincidencias, devolveremos un ArrayList<Alumno> con los alumnos encontrados.
Si no encontramos coincidencias, devolveremos una lista vacía.
El repositorio no debe mostrar mensajes por consola.
3. Incorporar la búsqueda a AlumnoService
La capa de negocio debe permitir que Main solicite búsquedas sin acceder directamente al repositorio.
Añadir a AlumnoService:
public ArrayList<Alumno> buscarPorNombre(String nombre)
        throws IOException {
    // Pendiente
}
Requisitos
El método deberá:
Recibir el nombre que queremos buscar.
Llamar al método buscarPorNombre() del repositorio.
Devolver la lista obtenida.
Pista
Recordemos cómo utilizábamos el repositorio para obtener todos los alumnos:
repository.obtenerTodos();
Ahora utilizaremos un método diferente, que recibe un parámetro.
Importante: AlumnoService no debe recorrer el fichero ni realizar operaciones de lectura.
La búsqueda de los registros corresponde a AlumnoRepository.
4. Añadir la búsqueda al menú de Main
Modificaremos el menú:
===== GESTIÓN DE ALUMNOS =====
1. Registrar alumno
2. Mostrar alumnos
3. Buscar alumno
4. Salir
Selecciona una opción:
Requisitos
Crear en Main un método auxiliar:
private static void buscarAlumno(
        Scanner sc, AlumnoService service) {
    // Pendiente
}
El método deberá:
Solicitar al usuario el nombre que desea buscar.
Guardar el texto introducido en un String.
Llamar a service.buscarPorNombre().
Recibir el ArrayList<Alumno> con los resultados.
Comprobar si la lista está vacía.
Si está vacía, informar de que no se encontraron coincidencias.
Si contiene alumnos, recorrerla utilizando un for-each.
Mostrar cada alumno utilizando su método toString().
Capturar las posibles excepciones de entrada/salida.
Ejemplo de ejecución
===== GESTIÓN DE ALUMNOS =====
1. Registrar alumno
2. Mostrar alumnos
3. Buscar alumno
4. Salir
Selecciona una opción: 3
Introduce el nombre que deseas buscar: ana
===== RESULTADOS =====
Ana García - 20 años - DAM
Mariana Pérez - 19 años - DAM
Si no encontramos coincidencias:
Introduce el nombre que deseas buscar: Roberto
No se encontraron alumnos con ese nombre.
Comprobación
Realizar las siguientes búsquedas:
Un nombre completo que exista.
Una parte de un nombre.
Un nombre escrito en minúsculas.
Un nombre escrito en mayúsculas.
Un nombre que no exista.
Importante: buscar alumnos no debe modificar el contenido del fichero.
PARTE 2 — Eliminar alumnos
5. ¿Cómo eliminamos un registro de un fichero de texto?
Hasta ahora utilizábamos StandardOpenOption.APPEND para añadir alumnos al final del fichero.
Pero ahora queremos eliminar un alumno que puede encontrarse en cualquier posición.
Supongamos que tenemos:
Ana;20;DAM
Pedro;22;DAW
Lucia;19;DAM
Carlos;25;ASIR
Y queremos eliminar a Pedro.
El resultado esperado será:
Ana;20;DAM
Lucia;19;DAM
Carlos;25;ASIR
No podemos eliminar directamente una línea intermedia utilizando BufferedWriter.
Para resolverlo, utilizaremos el siguiente procedimiento:
         alumnos.txt
              |
              | Leer todos
              v
      ArrayList<Alumno>
              |
              | Eliminar un alumno
              v
      ArrayList<Alumno>
        (actualizado)
              |
              | Sobrescribir fichero
              v
         alumnos.txt
Algoritmo
Leer todos los alumnos del fichero.
Almacenarlos en un ArrayList<Alumno>.
Buscar el alumno que queremos eliminar.
Eliminarlo de la colección.
Abrir el fichero en modo sobrescritura.
Recorrer la colección actualizada.
Escribir nuevamente todos los alumnos restantes.
De esta forma, el fichero conservará únicamente los registros que no hemos eliminado.
6. Implementar eliminarPorNombre() en AlumnoRepository
Añadir a AlumnoRepository:
public boolean eliminarPorNombre(String nombre)
        throws IOException {
    // Pendiente
}
El método devolverá:
true si se encontró y eliminó un alumno.
false si no se encontró ningún alumno con ese nombre.
Parte A — Recuperar los alumnos
Requisitos:
Llamar al método obtenerTodos().
Guardar el resultado en un ArrayList<Alumno>.
Pista:
ArrayList<Alumno> alumnos = obtenerTodos();
No necesitamos volver a implementar la lectura del fichero.
Estamos reutilizando un método existente.
Parte B — Buscar y eliminar de la colección
Recorrer la lista hasta encontrar un alumno cuyo nombre coincida con el recibido como parámetro.
Para simplificar esta versión:
Supondremos que no existen dos alumnos con el mismo nombre completo.
La eliminación requerirá introducir el nombre completo.
No distinguiremos entre mayúsculas y minúsculas.
Eliminaremos únicamente el primer alumno coincidente.
Requisitos
Crear una variable boolean para indicar si encontramos al alumno.
Recorrer el ArrayList<Alumno> mediante un bucle for tradicional.
Obtener el nombre de cada alumno mediante su getter.
Compararlo con el nombre recibido como parámetro.
Utilizar equalsIgnoreCase() para ignorar mayúsculas y minúsculas.
Si encontramos una coincidencia, eliminar el alumno de la colección.
Actualizar la variable boolean.
Interrumpir el bucle mediante break.
Pistas
Para comparar dos nombres:
alumno.getNombre().equalsIgnoreCase(nombre);
Para obtener un elemento de una lista mediante su posición:
Alumno alumno = alumnos.get(i);
Para eliminar un elemento mediante su posición:
alumnos.remove(i);
Para interrumpir un bucle:
break;
Importante: utilizaremos un for tradicional con índice.
No utilizaremos removeIf(), Streams ni expresiones lambda.
Parte C — Comprobar si encontramos al alumno
Si no encontramos ningún alumno:
No debemos modificar el fichero.
El método debe devolver false.
Si encontramos al alumno:
Continuaremos con la reescritura del fichero.
Devolveremos true cuando la operación termine correctamente.
7. Reescribir el fichero
Una vez eliminado el alumno del ArrayList, necesitamos actualizar alumnos.txt.
Hasta ahora, para registrar alumnos utilizábamos:
StandardOpenOption.CREATE,
StandardOpenOption.APPEND
Pero APPEND añade información al final del fichero.
Ahora necesitamos sustituir el contenido anterior por el contenido actualizado.
Para ello utilizaremos:
StandardOpenOption.CREATE,
StandardOpenOption.TRUNCATE_EXISTING
¿Qué hace TRUNCATE_EXISTING?
Si el fichero ya existe, elimina su contenido al abrirlo para escritura.
Esto nos permite escribir nuevamente todos los alumnos que permanecen en la colección.
Requisitos
Abrir el fichero mediante Files.newBufferedWriter().
Utilizar StandardOpenOption.CREATE.
Utilizar StandardOpenOption.TRUNCATE_EXISTING.
Utilizar try-with-resources.
Recorrer el ArrayList<Alumno> actualizado mediante un for-each.
Obtener los datos de cada alumno mediante sus getters.
Construir una cadena con el formato Nombre;Edad;Ciclo.
Escribir cada registro mediante write().
Añadir un salto de línea mediante newLine().
Devolver true cuando la operación haya terminado correctamente.
Pista
La estructura de escritura será similar a la que utilizamos en guardar():
try (BufferedWriter writer = Files.newBufferedWriter(
        ruta,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING)) {
    // Recorrer los alumnos restantes
    // Construir cada registro
    // Escribir cada registro
}
Atención: no debemos utilizar APPEND para esta operación.
Si lo utilizáramos, volveríamos a añadir los alumnos al final del fichero sin eliminar los registros anteriores.
Ejemplo
Contenido inicial:
Ana;20;DAM
Pedro;22;DAW
Lucia;19;DAM
Carlos;25;ASIR
Después de eliminar a Pedro:
Ana;20;DAM
Lucia;19;DAM
Carlos;25;ASIR
El fichero debe conservar el mismo formato que utilizábamos anteriormente.
Nota: en esta primera versión reescribiremos directamente el fichero original. Más adelante estudiaremos cómo utilizar ficheros temporales para evitar perder información si ocurre un error durante la escritura.
8. Incorporar la eliminación a AlumnoService
La capa de negocio será la encargada de coordinar la eliminación.
Añadir a AlumnoService:
public boolean eliminarAlumno(String nombre)
        throws IOException {
    // Pendiente
}
Requisitos
Recibir el nombre del alumno como parámetro.
Llamar al método eliminarPorNombre() del repositorio.
Devolver el resultado de la operación.
Importante: AlumnoService no debe utilizar BufferedReader, BufferedWriter ni acceder directamente al fichero.
El repositorio será responsable de modificar los datos almacenados.
9. Añadir la eliminación al menú de Main
Nuestro menú tendrá ahora cinco opciones:
===== GESTIÓN DE ALUMNOS =====
1. Registrar alumno
2. Mostrar alumnos
3. Buscar alumno
4. Eliminar alumno
5. Salir
Selecciona una opción:
Requisitos
Crear en Main el siguiente método:
private static void eliminarAlumno(
        Scanner sc, AlumnoService service) {
    // Pendiente
}
El método deberá:
Solicitar el nombre completo del alumno que queremos eliminar.
Guardar el nombre en un String.
Llamar a service.eliminarAlumno().
Recibir el resultado de la operación en una variable boolean.
Si devuelve true, mostrar un mensaje de confirmación.
Si devuelve false, informar de que no se encontró al alumno.
Capturar las posibles excepciones de entrada/salida.
Ejemplo de ejecución
===== GESTIÓN DE ALUMNOS =====
1. Registrar alumno
2. Mostrar alumnos
3. Buscar alumno
4. Eliminar alumno
5. Salir
Selecciona una opción: 4
Introduce el nombre del alumno: Pedro
Alumno eliminado correctamente.
Si el alumno no existe:
Introduce el nombre del alumno: Roberto
No se encontró ningún alumno con ese nombre.
Si ocurre un problema al modificar el fichero:
No se pudo eliminar al alumno.
Importante: el mensaje de eliminación correcta solamente debe mostrarse si el repositorio ha terminado la operación sin errores.
PARTE 3 — Comprobar el funcionamiento
10. Pruebas de la aplicación
Una vez implementadas las dos funcionalidades, realizaremos las siguientes pruebas.
Prueba 1 — Registrar alumnos
Registrar los siguientes alumnos:
Ana García;20;DAM
Pedro López;22;DAW
Mariana Pérez;19;DAM
Carlos Rodríguez;25;ASIR
Comprobar que aparecen correctamente al seleccionar la opción de mostrar alumnos.
Prueba 2 — Buscar alumnos
Buscar:
ana
Resultado esperado:
Ana García - 20 años - DAM
Mariana Pérez - 19 años - DAM
Prueba 3 — Eliminar un alumno
Eliminar:
Pedro López
Comprobar que el programa muestra:
Alumno eliminado correctamente.
Prueba 4 — Comprobar el listado
Mostrar nuevamente los alumnos.
Resultado esperado:
===== ALUMNOS REGISTRADOS =====
Ana García - 20 años - DAM
Mariana Pérez - 19 años - DAM
Carlos Rodríguez - 25 años - ASIR
Pedro ya no debe aparecer.
Prueba 5 — Comprobar la persistencia
Salir de la aplicación.
Abrir manualmente datos/alumnos.txt.
Comprobar que Pedro ya no aparece en el fichero.
Ejecutar nuevamente la aplicación.
Mostrar los alumnos.
Comprobar que la eliminación se ha conservado.
Prueba 6 — Eliminar un alumno inexistente
Intentar eliminar:
Roberto
La aplicación deberá informar de que no existe.
El fichero no debe modificarse.
Prueba 7 — Eliminar todos los alumnos
Eliminar uno por uno todos los alumnos registrados.
Después, seleccionar la opción de mostrar alumnos.
Resultado esperado:
La lista de alumnos está vacía.
El fichero puede existir y estar vacío.
La aplicación debe seguir funcionando y permitir registrar nuevos alumnos.
11. Preguntas para responder
¿Qué responsabilidad tiene cada una de las tres capas?
¿Por qué implementamos buscarPorNombre() en AlumnoRepository?
¿Por qué Main no debe llamar directamente a los métodos del repositorio?
¿Por qué reutilizamos obtenerTodos() en lugar de volver a implementar la lectura del fichero?
¿Por qué no podemos eliminar directamente una línea intermedia utilizando BufferedWriter?
¿Qué diferencia existe entre APPEND y TRUNCATE_EXISTING?
¿Por qué no debemos reescribir el fichero si el alumno no existe?
¿Por qué eliminarPorNombre() devuelve un boolean?
¿Qué ocurriría si dos alumnos tuvieran exactamente el mismo nombre?
¿Qué problema podría producirse si el programa falla mientras reescribe el fichero?
¿Qué clases hemos tenido que modificar para incorporar estas funcionalidades?
¿Qué cambiaría en el proceso de búsqueda y eliminación si utilizáramos una base de datos en lugar de un fichero de texto?
Restricciones
Utilizar Java 21.
Partir del proyecto desarrollado en la versión 1.0.
Mantener las cuatro clases existentes.
No crear una nueva aplicación desde cero.
Utilizar BufferedReader y BufferedWriter para acceder al fichero.
Utilizar try-with-resources.
Utilizar ArrayList<Alumno> para trabajar con los registros.
No utilizar Files.readAllLines(), Files.readString() ni Files.writeString().
No utilizar Streams ni expresiones lambda.
No utilizar removeIf().
No utilizar Scanner fuera de Main.
No acceder a ficheros desde Main ni desde AlumnoService.
No llamar directamente a AlumnoRepository desde Main.
No mostrar mensajes por consola desde AlumnoRepository ni desde AlumnoService.
Mantener la gestión de excepciones de entrada/salida mediante IOException.
No modificar el formato de almacenamiento Nombre;Edad;Ciclo.
Objetivo final
Al terminar esta ampliación, nuestra aplicación deberá permitir:
GESTIÓN DE ALUMNOS — VERSIÓN 1.1
✓ Registrar alumnos.
✓ Mostrar alumnos.
✓ Buscar alumnos por nombre.
✓ Eliminar alumnos.
✓ Conservar los datos entre ejecuciones.
✓ Mantener separadas las tres capas.
Esta será nuestra versión 1.1.
En próximas ampliaciones incorporaremos nuevas funcionalidades, como modificar alumnos, importar y exportar datos y sustituir la persistencia en ficheros por una base de datos.
see less
Reply
has context menu
Post in channel