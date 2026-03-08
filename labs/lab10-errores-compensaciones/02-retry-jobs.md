# Retry de Jobs

## 🎯 Objetivo

Provocar un **fallo técnico en una Service Task** y observar cómo el motor Camunda aplica automáticamente la política de **reintentos (retries)**.

---

## 🧠 Contexto

No todos los errores son **errores de negocio (BPMN Error)**.

Muchos errores son **errores técnicos**, por ejemplo:

* un servicio externo no responde
* fallo de red
* excepción en el código Java

Cuando ocurre una excepción técnica, Camunda **no rompe el proceso inmediatamente**.

En su lugar:

```
crea un Job
aplica reintentos automáticos
si todos fallan → incidente
```

Este mecanismo se gestiona mediante el **Job Executor**.

---

# Convertir la Service Task en tarea asíncrona

Abrir el modelo:

```
model/approval-process.bpmn
```

Seleccionar la tarea:

```
Validar solicitud
```

En el panel de propiedades activar:

```
Asynchronous Before
```

Esto hará que la tarea se ejecute como un **Job del motor**.

Guardar el modelo.

---

# Copiar el modelo al backend

```bash
cp model/approval-process.bpmn backend/src/main/resources/processes/
```

---

# Modificar el JavaDelegate

Abrir:

```
ValidarSolicitudDelegate.java
```

Modificar el método `execute` para lanzar una excepción técnica.

```java
@Override
public void execute(DelegateExecution execution) {

    System.out.println("Ejecutando validación");

    throw new RuntimeException("Fallo técnico en validación");

}
```

Esto simula un fallo del sistema.

---

# Compilar el proyecto

En la **terminal**, desde la **raíz del repositorio** ejecuta `cd backend`. Luego:

Compilar:

```bash
mvn clean package
```

---

# Ejecutar la aplicación

Arrancar la aplicación:

```bash
mvn spring-boot:run
```

Cuando el proceso llegue a la Service Task se producirá la excepción.

---

# Observar los reintentos

Abrir **Cockpit**:

```
http://localhost:8081/camunda
```

Ir a:

```
Process Instances
```

Abrir la instancia del proceso.

La tarea fallará y el motor programará reintentos.

En la sección **Jobs** se podrá observar algo similar a:

```
Retries: 3
```

Después de cada fallo el número disminuirá:

```
Retries: 2
Retries: 1
Retries: 0
```

---

# Qué ocurre cuando se agotan los retries

Cuando los reintentos llegan a **0**, el motor crea un:

```
Incident
```

Esto indica que el proceso está bloqueado debido a un error técnico.

---

# Flujo interno del motor

Cuando ocurre una excepción técnica:

```
Service Task ejecutada como Job
        │
        ▼
RuntimeException
        │
        ▼
Job falla
        │
        ▼
Retries--
        │
        ▼
nuevo intento programado
```

---

# Comparación con BPMN Error

| Tipo de error     | Comportamiento                |
| ----------------- | ----------------------------- |
| BPMN Error        | flujo alternativo del proceso |
| Runtime Exception | reintentos automáticos        |

---

# Comprobación

El ejercicio se considera completado cuando:

* la Service Task es **asíncrona**
* el delegate lanza una excepción
* Camunda crea un **Job con retries**
* los retries disminuyen después de cada fallo.
