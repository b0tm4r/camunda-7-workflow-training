# Arranque del motor Camunda

## 🎯 Objetivo

Verificar que el **motor Camunda se inicia automáticamente** cuando arranca la aplicación Spring Boot.

---

## 🧠 Contexto

Cuando se utiliza el starter de Camunda para Spring Boot, el motor de procesos se inicializa automáticamente durante el arranque de la aplicación.

Esto significa que no es necesario crear manualmente el **Process Engine**.

Spring Boot detecta la dependencia Camunda y configura automáticamente:

* el motor de procesos
* la conexión con la base de datos
* el despliegue de procesos BPMN

---

# Arrancar la aplicación

Abre una **terminal** (Terminal → New Terminal). Desde la **raíz del repositorio** ejecuta `cd workflow-app` y luego:

```bash
mvn spring-boot:run
```

Durante el arranque aparecerán varios mensajes en la terminal.

---

# Identificar el arranque del motor

En la **terminal** donde está arrancada la aplicación, revisa el texto que va apareciendo. Busca un mensaje similar a:

```
ENGINE-00001 Process Engine default created
```

Este mensaje indica que el **Process Engine ha sido creado correctamente**.

---

# Identificar el despliegue de procesos

Durante el arranque el motor también despliega automáticamente los procesos BPMN encontrados en el proyecto.

Buscar en los logs mensajes relacionados con:

```
deployment
```

Esto indica que el motor está cargando los modelos BPMN disponibles.

---

# Verificar desde el código

En el explorador de VS Code, abre la **clase principal** de la aplicación (por ejemplo **workflow-app/src/main/java/.../WorkflowAppApplication.java**; usa Ctrl+P y escribe el nombre si no la ves). Busca en el archivo el uso del servicio:

```java
RuntimeService
```

Este servicio solo está disponible si el **Process Engine ha sido creado correctamente**.

---

# Comprobación adicional

Añadir temporalmente el siguiente código en el `CommandLineRunner`:

```java
System.out.println("Camunda engine is running");
```

Volver a ejecutar la aplicación.

Si el mensaje aparece en consola junto con los logs del motor, significa que la aplicación y el motor Camunda se han iniciado correctamente.

---

# Qué ocurre durante el arranque

Cuando la aplicación inicia, ocurre la siguiente secuencia:

```
Spring Boot arranca
        │
        ▼
Camunda Process Engine se inicializa
        │
        ▼
Se despliegan los procesos BPMN
        │
        ▼
El motor queda listo para ejecutar procesos
```

---

# Comprobación

El ejercicio se considera completado cuando:

* la aplicación arranca correctamente
* aparece el mensaje de creación del **Process Engine**
* se identifican en los logs mensajes relacionados con el despliegue de procesos BPMN.

---

## 📚 Referencia

[Arquitectura de Camunda – referencia](../../docs/referencia/arquitectura-camunda.md)
