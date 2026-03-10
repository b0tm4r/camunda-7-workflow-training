# Acceso a Camunda Cockpit

## 🎯 Objetivo

Acceder a **Camunda Cockpit** para explorar el motor de procesos y verificar que la aplicación web de Camunda está disponible.

---

## 🧠 Contexto

Cuando se utiliza la dependencia:

```id="p6hmbq"
camunda-bpm-spring-boot-starter-webapp
```

Camunda instala automáticamente varias aplicaciones web de administración.

Una de ellas es **Cockpit**.

Cockpit permite:

* visualizar procesos desplegados
* inspeccionar instancias de proceso
* analizar el estado del motor
* investigar errores o incidentes

**Referencia — Cómo diseñar el proceso y subirlo:** Para que Cockpit muestre procesos e instancias (y Tasklist muestre tareas humanas), hace falta un BPMN con al menos una User Task desplegado en la aplicación. Cómo diseñarlo en **Camunda Modeler** y copiarlo/desplegarlo en el proyecto se explica en el **Lab 04 — Modelado de procesos** (crear modelo, eventos, Service Task, User Task y despliegue en `05-despliegue-proceso.md`). **Para que este lab funcione**, el modelo que debes ejecutar es el **approval** entregado en el repositorio: copia `model/approval-process.bpmn` a `workflow-app/src/main/resources/processes/` y arranca la aplicación; así verás el proceso en Cockpit (y, al iniciar una instancia, tareas en Tasklist).

---

# Arrancar la aplicación

Abre una **terminal**. Desde la **raíz del repositorio** (donde están **labs** y **workflow-app**) ejecuta `cd workflow-app` y luego arranca la aplicación:

```bash id="ju2z7b"
mvn spring-boot:run
```

Espera a que en la **terminal** aparezca un mensaje tipo `Tomcat started on port(s): 8081` (o el puerto que tengas en **application.properties**; si no cambiaste nada, puede ser 8080 u 8081).

---

# Abrir Camunda Cockpit

Abre el **navegador** y en la barra de direcciones escribe:

```
http://localhost:8081/camunda
```

(Si tu aplicación usa otro puerto, cambia **8081** por ese número; el puerto aparece en el mensaje de arranque de la terminal.)

Deberías ver la **pantalla de login** de Camunda.

---

# Problema frecuente: redirecciones incorrectas

En algunos entornos (por ejemplo laboratorios remotos o plataformas de formación) al abrir `/camunda` el navegador puede ser **redirigido continuamente** a un dominio/puerto incorrecto (por ejemplo, añadiendo `:8080` cuando tú no estás usando ese puerto).

En ese caso puedes ajustar la configuración de Spring Boot para que las redirecciones se construyan correctamente:

```yaml
server:
  forward-headers-strategy: framework
```

Si además necesitas fijar un puerto concreto (por ejemplo `8080` o `8081`), puedes incluirlo en el mismo bloque:

```yaml
server:
  port: 8081
  forward-headers-strategy: framework
```

Tras aplicar esta configuración y **reiniciar la aplicación**, vuelve a acceder a la URL de Cockpit indicada por el laboratorio. Si el problema persiste, revisa que el puerto configurado en `server.port` coincida con el puerto que aparece en los logs de arranque (`Tomcat started on port(s): ...`).

---

# Iniciar sesión

Si en **application.properties** (o **application.yml**) configuraste usuario y contraseña para Camunda, úsalos. Por ejemplo suele ser **demo** / **demo**. Si no configuraste nada, algunas instalaciones permiten acceso sin login; si pide credenciales y no las tienes, añade en application.properties las propiedades de usuario/contraseña de Camunda (consulta la documentación del starter).

---

# Explorar Cockpit

Una vez dentro de Cockpit observar las secciones disponibles.

Entre ellas:

* **Processes**
* **Decisions**
* **Deployments**
* **Jobs**

---

# Ver procesos desplegados

Dentro de Cockpit, en el **menú o la barra lateral izquierda** (según la versión) haz clic en **Processes** (o "Procesos"). Ahí deberían listarse los procesos BPMN desplegados, los mismos que están en:

```
src/main/resources/processes
```

Por ejemplo:

```
approval
```

Esto confirma que el motor ha desplegado correctamente el proceso.

---

# Explorar un proceso

Haz **clic** en uno de los procesos de la lista. Cockpit mostrará para ese proceso:

* el diagrama BPMN
* estadísticas de ejecución
* instancias activas
* historial de procesos

---

# Qué hemos comprobado

Con este ejercicio se ha verificado que:

* el motor Camunda está activo
* los procesos BPMN se despliegan automáticamente
* Cockpit permite inspeccionar el estado del motor

---

# Comprobación

El ejercicio se considera completado cuando:

* la aplicación arranca correctamente
* se puede acceder a `/camunda`
* se visualizan procesos desplegados dentro de Cockpit.
