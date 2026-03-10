# Acceso a Camunda Tasklist

## 🎯 Objetivo

Acceder a **Camunda Tasklist** para gestionar las tareas de usuario (User Tasks) que genera el motor cuando un proceso llega a una tarea humana.

---

## 🧠 Contexto

Junto con Cockpit, Camunda incluye **Tasklist**: la aplicación donde los usuarios **ven y completan sus tareas**.

Tasklist permite:

* ver las tareas asignadas a un usuario o grupo
* abrir una tarea y completarla
* filtrar por proceso o fecha
* consultar variables y formularios asociados

La URL de Tasklist suele ser la misma base que Cockpit, con la ruta de la aplicación Tasklist.

**Referencia — Cómo diseñar el proceso y subirlo:** Para que Tasklist muestre tareas hace falta un BPMN con al menos una **User Task** (por ejemplo un flujo de aprobación) desplegado en la aplicación. Cómo diseñarlo en **Camunda Modeler** y subirlo al proyecto se explica en el **Lab 04 — Modelado de procesos** (crear modelo, User Task y despliegue en `05-despliegue-proceso.md`).

---

# Arrancar la aplicación

Abre una **terminal**. Desde la **raíz del repositorio** ejecuta `cd workflow-app` y luego:

```bash
mvn spring-boot:run
```

Espera a que en la terminal aparezca el mensaje del puerto (por ejemplo `Tomcat started on port(s): 8081`). Anota el puerto (8080, 8081 u otro según tu **application.properties**).

---

# Abrir Camunda Tasklist

Abre el **navegador** y accede a:

```
http://localhost:8081/camunda/app/tasklist
```

(Si tu aplicación usa otro puerto, cambia **8081** por ese número.)

Tras iniciar sesión (las mismas credenciales que en Cockpit: por ejemplo **demo** / **demo** si las configuraste en application.properties), entrarás en **Tasklist**.

---

# Explorar Tasklist

En la pantalla principal verás la **lista de tareas**. Si aún no has ejecutado ningún proceso con User Tasks, la lista puede estar vacía; es normal.

En la **barra superior o el menú** suelen estar:

* **Tasks** (o "Tareas"): lista de tareas pendientes
* Filtros por asignee, proceso, etc.
* Al hacer clic en una tarea, se abre el detalle y desde ahí se puede **completar** la tarea (botón "Complete" o "Completar").

Cuando en labs posteriores tengas procesos con User Tasks, esas tareas aparecerán aquí asignadas al usuario configurado.

---

# Qué hemos comprobado

Con este ejercicio se ha verificado que:

* Tasklist está disponible en la misma instalación que Cockpit
* se puede acceder con el mismo usuario y contraseña
* la lista de tareas está lista para cuando haya procesos con User Tasks

---

# Comprobación

El ejercicio se considera completado cuando:

* la aplicación arranca correctamente
* se puede acceder a **/camunda/app/tasklist** (o la ruta que use tu versión)
* se ve la interfaz de Tasklist (aunque la lista de tareas esté vacía).
