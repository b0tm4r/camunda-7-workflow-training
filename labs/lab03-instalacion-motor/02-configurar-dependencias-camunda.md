# Añadir y validar dependencias (Camunda, JPA, H2)

## 🎯 Objetivo

**Añadir** al **pom.xml** las dependencias de Camunda, JPA y H2 **una a una**, y **validar** después de cada una (compilar y, al final, arrancar). Así se monta el proyecto como en un proceso real de instalación: cada pieza se añade y se comprueba antes de seguir.

---

## 🧠 Contexto

En el lab00 generaste **workflow-app** con el archetype de Camunda; el pom puede traer ya parte de las dependencias. Aquí **revisas el pom**, añades lo que falte (Camunda, JPA, H2) y validas tras cada cambio:

1. **Camunda** (motor y consolas web)
2. **Spring Data JPA** (persistencia para el motor)
3. **H2** (base de datos embebida)

Tras cada dependencia ejecuta **mvn clean install** para validar. Al final añadirás la configuración de la base de datos en **application.properties** y validarás arrancando la aplicación. Si el archetype ya incluyó alguna dependencia, **no la dupliques**; comprueba solo que las versiones coincidan con el pom objetivo (ver más abajo).

**Antes de empezar:** Revisa en el **pom.xml** el `<parent>`: debe ser **spring-boot-starter-parent** con **version 2.7.18**. Si el archetype generó otra (p. ej. 3.x), cámbiala a **2.7.18** y guarda para evitar problemas en el resto del curso.

---

## ✏️ 1. Camunda en el pom

Abre **workflow-app/pom.xml**. Revisa si ya existe la dependencia de Camunda. **Si no está**, añádela dentro de `<dependencies>` (después de `spring-boot-starter-web`, antes de `spring-boot-starter-test`). **Si ya está**, comprueba que la versión sea **7.19.0**; si no, cámbiala.

```xml
		<dependency>
			<groupId>org.camunda.bpm.springboot</groupId>
			<artifactId>camunda-bpm-spring-boot-starter-webapp</artifactId>
			<version>7.19.0</version>
		</dependency>
```

Guarda el archivo.

### Validar

En la terminal, desde **workflow-app**:

```bash
cd workflow-app
mvn clean install
```

Debe terminar con **BUILD SUCCESS**. Si falla, revisa la etiqueta `<dependency>` (cierre, indentación). Con solo Camunda el proyecto compila; al arrancar más adelante necesitará base de datos (la añadimos a continuación).

---

## ✏️ 2. Spring Data JPA en el pom

Si **no está** `spring-boot-starter-data-jpa`, añádela (después de Camunda, antes de `spring-boot-starter-test`). Si ya está, no la dupliques.

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
```

Guarda.

### Validar

```bash
mvn clean install
```

Debe terminar con **BUILD SUCCESS**.

---

## ✏️ 3. H2 en el pom

Si **no está** la dependencia **h2** (`com.h2database`), añádela (después de JPA, antes de `spring-boot-starter-test`) con `<scope>runtime</scope>`. Si ya está, no la dupliques.

```xml
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
```

Guarda.

### Validar

```bash
mvn clean install
```

Debe terminar con **BUILD SUCCESS**.

---

## ✏️ 4. Configurar la base de datos en application.properties

Abre **workflow-app/src/main/resources/application.properties**. Ahora mismo solo tienes `server.port=8080`. **Añade** (o sustituye) con la configuración de H2 y JPA para que Camunda pueda arrancar:

```properties
server.port=8080

spring.datasource.url=jdbc:h2:mem:camunda;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

Guarda.

---

## ✅ Validar: arrancar la aplicación

Desde **workflow-app**:

```bash
mvn spring-boot:run
```

La aplicación debe arrancar. En los logs deberías ver algo como **Process Engine default created**. Eso confirma que Camunda, JPA y H2 están bien integrados.

Para detener: **Ctrl+C**.

---

## 🔎 Opcional: ver el árbol de dependencias

Para ver qué ha traído cada dependencia:

```bash
mvn dependency:tree
```

En la salida aparecerán, entre otras, referencias a `camunda-engine`, `camunda-webapp`, etc.

---

## ✅ Comprobación

El ejercicio se considera completado cuando:

* has **añadido** al pom las tres dependencias (Camunda, JPA, H2) **una a una** y has **validado** con `mvn clean install` tras los cambios
* has **configurado** **application.properties** con H2 y JPA
* **mvn spring-boot:run** arranca la aplicación y aparece el mensaje del Process Engine en los logs

---

## 📋 Pom objetivo (al terminar este paso)

Al acabar este paso, tu **pom.xml** debe quedar así para que el resto del curso funcione sin problemas de entorno:

* **Parent:** `spring-boot-starter-parent` **2.7.18**
* **Java:** `java.version` **17**
* **Dependencias:** `spring-boot-starter-web`; **Camunda** `camunda-bpm-spring-boot-starter-webapp` **7.19.0**; `spring-boot-starter-data-jpa`; **h2** `runtime`; `spring-boot-starter-test` (test).

Si el archetype generó un **parent distinto** (p. ej. 3.x) u otra versión de Camunda, **cámbialos a 2.7.18 y 7.19.0** para evitar incompatibilidades en los siguientes labs.

En el siguiente paso (Configuración básica) se revisa la configuración; en **Arranque del motor** volverás a arrancar y a usar Cockpit y Tasklist.
