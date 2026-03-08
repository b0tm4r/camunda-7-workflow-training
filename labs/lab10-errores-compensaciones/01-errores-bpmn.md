# Errores BPMN

## 🎯 Objetivo

Simular un **error dentro de una Service Task** y observar cómo el motor Camunda gestiona los errores durante la ejecución de un proceso.

---

## 🧠 Contexto

En BPMN pueden ocurrir errores durante la ejecución de una tarea.

Por ejemplo:

* fallo al llamar a una API
* error en la lógica de negocio
* datos inválidos

Camunda permite gestionar estos errores utilizando **BPMN Errors**.

Un error BPMN permite que el proceso **capture el fallo y redirija el flujo a otra parte del proceso**.

---

# Abrir el modelo BPMN

Abrir el archivo:

```
model/approval-process.bpmn
```

Editar el modelo usando **Camunda Modeler**.

---

# Añadir un Boundary Event de error

Seleccionar la tarea:

```
Validar solicitud
```

Añadir un **Boundary Event** al borde de la tarea.

Seleccionar el tipo:

```
Error Boundary Event
```

Este evento permitirá capturar errores producidos durante la ejecución de la tarea.

---

# Crear un flujo alternativo

Desde el **Boundary Event** crear un nuevo flujo.

Este flujo representará el manejo del error.

Ejemplo:

```
Validar solicitud
       │
       ├── flujo normal → Aprobar solicitud
       │
       └── error → Fin error
```

Añadir un **End Event** llamado:

```
Solicitud inválida
```

---

# Guardar el modelo

Guardar el archivo:

```
model/approval-process.bpmn
```

Copiar el modelo al backend:

```bash
cp model/approval-process.bpmn backend/src/main/resources/processes/
```

---

# Modificar el JavaDelegate

Abrir la clase:

```
ValidarSolicitudDelegate.java
```

Modificar el código para lanzar un error BPMN.

```java
import org.camunda.bpm.engine.delegate.BpmnError;

@Override
public void execute(DelegateExecution execution) {

    Integer importe = (Integer) execution.getVariable("importe");

    if (importe > 5000) {
        throw new BpmnError("IMPORTE_INVALIDO");
    }

    execution.setVariable("estadoSolicitud", "VALIDADA");
}
```

---

# Configurar el código de error

Seleccionar el **Boundary Error Event** en el modelo BPMN.

Configurar el campo:

```
Error Code
```

Valor:

```
IMPORTE_INVALIDO
```

---

# Compilar el proyecto

En la **terminal**, desde la **raíz del repositorio** ejecuta `cd backend`. Luego:

Compilar:

```bash
mvn clean package
```

---

# Ejecutar la aplicación

Arrancar el backend:

```bash
mvn spring-boot:run
```

---

# Probar el error

Iniciar el proceso con una variable:

```
importe = 8000
```

Cuando el proceso llegue a **Validar solicitud**, el delegate lanzará el error.

El flujo del proceso seguirá el camino del **Boundary Event**.

---

# Flujo del proceso

Flujo normal:

```
Validar solicitud
       │
       ▼
Aprobar solicitud
```

Flujo de error:

```
Validar solicitud
       │
       ▼
Error Boundary Event
       │
       ▼
Solicitud inválida
```

---

# Ver el resultado en Cockpit

Abrir:

```
http://localhost:8081/camunda
```

Ir a **Cockpit**.

Seleccionar el proceso.

Observar que la instancia del proceso termina en el flujo de error.

---

# Qué ocurre internamente

Cuando se lanza un error BPMN:

```
Service Task lanza BpmnError
        │
        ▼
El motor busca un Error Boundary Event
        │
        ▼
El flujo continúa por el camino de error
```

Esto permite manejar fallos dentro del modelo BPMN.

---

# Comprobación

El ejercicio se considera completado cuando:

* el proceso tiene un **Boundary Error Event**
* el delegate lanza un `BpmnError`
* el proceso sigue el flujo de error cuando se produce el fallo.
