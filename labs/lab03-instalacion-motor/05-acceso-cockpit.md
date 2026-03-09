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
