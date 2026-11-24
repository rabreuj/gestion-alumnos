# Sistema de gestión de alumnos (versión 1.2)

## Objetivos

En la versión anterior desarrollamos una aplicación para registrar alumnos y consultar los datos almacenados en un archivo de texto, organizando el código en tres capas.

En esta nueva versión vamos a migrar nuestra aplicación a un proyecto Maven.

El objetivo es aprender a utilizar una estructura de proyecto estandarizada, organizar nuestras clases mediante paquetes y gestionar la compilación de la aplicación utilizando Maven.

**No vamos a incorporar nuevas funcionalidades.** La aplicación deberá comportarse exactamente igual que en la versión anterior.

---

## 1. Crear el proyecto Maven

### Requisitos

1. Crear un nuevo proyecto Maven desde el IDE.
2. Seleccionar Java 21 como versión del JDK.
3. Configurar los siguientes datos del proyecto:
   - GroupId: `com.cesur`
   - ArtifactId: `gestion-alumnos`
   - Version: `1.2.0`
4. Comprobar que se ha generado el archivo `pom.xml`.
5. Identificar el directorio `src/main/java`.

### Estructura inicial

```text
gestion-alumnos/
├── pom.xml
└── src/
    └── main/
        └── java/
```

Por ahora, no necesitamos incorporar ninguna dependencia externa.

---

## 2. Configurar el archivo pom.xml

El archivo `pom.xml` contiene la información y configuración de nuestro proyecto Maven.

Comprueba que contiene los siguientes elementos:

```xml
<groupId>com.cesur</groupId>
<artifactId>gestion-alumnos</artifactId>
<version>1.2.0</version>
```

Dentro de la sección `properties`, configura la versión de Java y la codificación de caracteres:

```xml
<properties>
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

Si el IDE ya ha generado estas propiedades, comprueba que sus valores sean correctos.

**Importante:** no es necesario sustituir todo el contenido del `pom.xml`. Solamente debemos comprobar y adaptar su configuración.

---

## 3. Organizar el proyecto mediante paquetes

En la versión anterior, todas nuestras clases estaban en el mismo directorio.

Ahora vamos a utilizar paquetes para organizar el código según las responsabilidades de cada clase.

### Organización de las clases

| Clase | Paquete |
|---|---|
| `Main` | `com.cesur.gestionalumnos` |
| `Alumno` | `com.cesur.gestionalumnos.model` |
| `AlumnoService` | `com.cesur.gestionalumnos.service` |
| `AlumnoRepository` | `com.cesur.gestionalumnos.repository` |

### Estructura esperada

```text
gestion-alumnos/
├── pom.xml
├── datos/
│   └── alumnos.txt
└── src/
    └── main/
        └── java/
            └── com/
                └── cesur/
                    └── gestionalumnos/
                        ├── Main.java
                        ├── model/
                        │   └── Alumno.java
                        ├── service/
                        │   └── AlumnoService.java
                        └── repository/
                            └── AlumnoRepository.java
```

### Requisitos

1. Crear el paquete principal `com.cesur.gestionalumnos`.
2. Crear los paquetes `model`, `service` y `repository` dentro del paquete principal.
3. Copiar las clases de la versión anterior a sus paquetes correspondientes.

**Importante:** no debemos volver a programar las clases. Vamos a reutilizar el código que ya tenemos.

---

## 4. Adaptar las clases a los paquetes

Al mover las clases a diferentes paquetes, debemos indicar a Java dónde se encuentra cada una.

### Parte A — Declarar los paquetes

Cada clase deberá comenzar con la declaración de su paquete correspondiente.

Por ejemplo, en `Alumno.java`:

```java
package com.cesur.gestionalumnos.model;

public class Alumno {

    // Código de la versión anterior
}
```

Realiza lo mismo con las otras tres clases.

### Parte B — Importar las clases necesarias

Ahora que las clases se encuentran en diferentes paquetes, será necesario utilizar `import` para acceder a ellas.

Por ejemplo, `AlumnoRepository` necesita utilizar la clase `Alumno`:

```java
package com.cesur.gestionalumnos.repository;

import com.cesur.gestionalumnos.model.Alumno;
```

### Requisitos

1. Identificar qué clases utiliza `AlumnoRepository`.
2. Identificar qué clases utiliza `AlumnoService`.
3. Identificar qué clases utiliza `Main`.
4. Añadir los imports correspondientes.
5. Comprobar que no existen errores de compilación.

**Pista:** el IDE puede ayudarnos a importar automáticamente las clases que necesitamos.

No debemos eliminar los imports de las bibliotecas estándar de Java que ya utilizábamos, como `ArrayList`, `Path`, `Files` o `IOException`.

---

## 5. Recuperar los datos de la versión anterior

Nuestra aplicación seguirá utilizando archivos de texto para almacenar los alumnos.

### Requisitos

1. Crear el directorio `datos` en la raíz del proyecto Maven, al mismo nivel que `pom.xml`.
2. Copiar el archivo `alumnos.txt` de la versión anterior.
3. Comprobar que la ruta utilizada por la aplicación sigue siendo correcta.

```java
Path ruta = Path.of("datos", "alumnos.txt");
```

El archivo deberá conservar su formato original:

```text
Ana;20;DAM
Pedro;22;DAW
Lucia;19;DAM
```

**Importante:** el directorio `datos` no debe estar dentro de `src/main/java` ni de `src/main/resources`.

Nuestros datos son archivos externos que la aplicación puede modificar durante su ejecución.

Comprueba también que el directorio de trabajo de la aplicación sea la raíz del proyecto.

---

## 6. Compilar el proyecto utilizando Maven

Hasta ahora utilizábamos principalmente el IDE para compilar y ejecutar nuestras aplicaciones.

Vamos a comprobar que Maven también puede encargarse de la compilación y el empaquetado del proyecto.

Desde la terminal, sitúate en el directorio donde se encuentra el archivo `pom.xml`.

### Parte A — Compilar

Ejecuta:

```bash
mvn compile
```

Si todo es correcto, Maven deberá mostrar:

```text
BUILD SUCCESS
```

Comprueba que se ha generado el directorio `target`.

Dentro de `target/classes` encontrarás las clases compiladas de nuestra aplicación.

### Parte B — Empaquetar

Ejecuta:

```bash
mvn package
```

Maven deberá generar un archivo JAR dentro del directorio `target`.

```text
target/
├── classes/
└── gestion-alumnos-1.2.0.jar
```

El archivo JAR contiene las clases compiladas de nuestra aplicación.

### Parte C — Limpiar y reconstruir

Ejecuta:

```bash
mvn clean
```

Comprueba qué ha ocurrido con el directorio `target`.

A continuación, ejecuta:

```bash
mvn clean package
```

Comprueba que Maven vuelve a generar el directorio y el archivo JAR.

Si el proyecto dispone de Maven Wrapper, también puedes utilizar `./mvnw` en Linux o macOS, o `mvnw.cmd` en Windows, en lugar de `mvn`.

---

## 7. Comprobar el funcionamiento

Ejecuta la clase `Main` desde el IDE.

La aplicación deberá conservar el menú de la versión anterior:

```text
===== GESTION DE ALUMNOS =====

1. Registrar alumno
2. Mostrar alumnos
3. Salir

Selecciona una opcion:
```

### Pruebas

1. Mostrar los alumnos almacenados en la versión anterior.
2. Comprobar que sus datos se muestran correctamente.
3. Registrar un nuevo alumno.
4. Mostrar nuevamente el listado.
5. Salir de la aplicación.
6. Ejecutar nuevamente el programa.
7. Comprobar que todos los alumnos siguen almacenados.
8. Abrir el archivo `alumnos.txt` y comprobar su contenido.
9. Ejecutar `mvn clean package` y comprobar que los datos de los alumnos se conservan.

### Resultado esperado

La aplicación debe funcionar exactamente igual que antes de la migración.

Aunque hemos cambiado la estructura del proyecto y su sistema de compilación, no hemos modificado sus funcionalidades.

---

## 8. Preguntas para responder

1. ¿Qué función cumple Maven dentro de nuestro proyecto?
2. ¿Para qué sirve el archivo `pom.xml`?
3. ¿Qué representan `groupId`, `artifactId` y `version`?
4. ¿Qué ventaja tiene organizar las clases mediante paquetes?
5. ¿Por qué ahora necesitamos importar nuestras propias clases?
6. ¿Qué diferencia existe entre `src/main/java` y `target/classes`?
7. ¿Qué diferencia existe entre `mvn compile` y `mvn package`?
8. ¿Qué ocurre cuando ejecutamos `mvn clean`?
9. ¿Por qué los datos de los alumnos no desaparecen al ejecutar `mvn clean`?
10. ¿Hemos necesitado modificar la lógica de nuestra aplicación para utilizar Maven?

---

## Restricciones

- Utilizar Java 21.
- Utilizar Maven como herramienta de construcción del proyecto.
- Mantener las cuatro clases de la versión anterior.
- Organizar las clases mediante los paquetes indicados.
- Mantener la arquitectura de tres capas.
- Mantener el almacenamiento mediante archivos de texto.
- No incorporar dependencias externas.
- No utilizar Jackson, Gson ni Lombok.
- No incorporar nuevas funcionalidades.
- Reutilizar el código de la versión anterior.

**Objetivo final:** migrar una aplicación Java existente a Maven, utilizando una estructura de proyecto estandarizada y manteniendo intacto su funcionamiento.

Esta será nuestra **versión 1.2**. Conservaremos el proyecto para incorporar nuevas funcionalidades y dependencias externas en próximas clases.