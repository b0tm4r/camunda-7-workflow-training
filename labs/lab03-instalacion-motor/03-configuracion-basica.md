# Configuración básica de Camunda

## 🎯 Objetivo

Explorar y modificar la configuración básica de la aplicación para comprobar cómo **Spring Boot carga automáticamente la configuración del sistema**.

---

## 🧠 Contexto

Spring Boot utiliza archivos de configuración para definir el comportamiento de la aplicación.

El archivo principal de configuración se encuentra en:

```
src/main/resources/application.properties
```

Desde este archivo es posible configurar:

* el puerto del servidor
* la base de datos
* propiedades del motor Camunda
* comportamiento del framework

En este ejercicio vamos a **modificar la configuración del servidor** para verificar que la aplicación utiliza el archivo de configuración.

---

# Abrir el archivo de configuración

En el explorador de VS Code, dentro de **workflow-app**, ve a **src** → **main** → **resources**. Abre el archivo **application.properties** (o **application.yml** si tu proyecto usa YAML). Si no existe, **clic derecho** en **resources** → **New File** y crea **application.properties**.

---

# Cambiar el puerto del servidor

Añadir la siguiente propiedad:

```
server.port=8081
```

Esta configuración indica que el servidor web debe arrancar en el puerto **8081** en lugar del puerto por defecto.

Guardar el archivo.

---

# Arrancar la aplicación

Abre una **terminal**. Desde la **raíz del repositorio** ejecuta `cd workflow-app` y luego:

```bash
mvn spring-boot:run
```

---

# Verificar el puerto del servidor

Durante el arranque aparecerá un mensaje similar a:

```
Tomcat started on port(s): 8081
```

Esto confirma que Spring Boot ha leído la configuración del archivo `application.properties`.

---

# Verificar desde el navegador

Abrir el navegador y acceder a:

```
http://localhost:8081
```

Si la aplicación responde correctamente significa que la configuración se ha aplicado.

---

# Qué significa esto para Camunda

Camunda utiliza el mismo mecanismo de configuración.

Esto permite configurar propiedades del motor desde el mismo archivo.

Por ejemplo:

```
camunda.bpm.admin-user.id=demo
camunda.bpm.admin-user.password=demo
```

Estas propiedades permiten crear automáticamente un usuario administrador para las aplicaciones web de Camunda.

---

# Comprobación

El ejercicio se considera completado cuando:

* se modifica el archivo `application.properties`
* la aplicación arranca en el puerto **8081**
* el navegador puede acceder a la aplicación usando el nuevo puerto.
