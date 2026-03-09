# Lab 03 — Instalación del motor Camunda

## 🎯 Objetivo

Configurar el **motor de procesos Camunda** dentro de la aplicación Spring Boot y verificar que el sistema está correctamente preparado para ejecutar procesos BPMN.

En este laboratorio se aprenderá a:

* explorar la estructura del proyecto Spring Boot (creado en lab00)
* **identificar y validar** las dependencias que el archetype de Camunda (lab00) ya añadió al pom
* arrancar el motor de procesos y comprobar que se inicia
* acceder a las aplicaciones web de Camunda (Cockpit, Tasklist)

---

# 🧠 Contexto

Camunda puede desplegarse de distintas formas, pero en este curso se utilizará el modo:

```text
Camunda embebido en Spring Boot
```

Esto significa que el **motor de procesos forma parte de la propia aplicación**.

La arquitectura del sistema será:

```text
Aplicación Spring Boot
        │
        ▼
Camunda Process Engine
        │
        ▼
Base de datos
```

Cuando la aplicación arranca:

* Spring Boot inicia el contexto
* Camunda arranca el Process Engine
* los modelos BPMN se despliegan automáticamente

---

# 🔎 Qué vamos a hacer en este laboratorio

En este laboratorio se comprobará que el motor Camunda está correctamente integrado en el proyecto.

Se realizarán ejercicios para:

* explorar la estructura del proyecto Spring Boot (workflow-app del lab00)
* **identificar** las dependencias en el pom y **validar** con Maven (dependency:tree, compile)
* arrancar la aplicación y verificar el motor en los logs
* acceder a Cockpit y Tasklist

---

# 📘 Ejercicios del laboratorio

### 01 — Estructura del proyecto Spring

Explorar la estructura del proyecto workflow-app y localizar los componentes principales.

Archivo:

```text
01-estructura-proyecto-spring.md
```

---

### 02 — Configurar dependencias Camunda

Identificar las dependencias necesarias en el `pom.xml` para integrar Camunda.

Archivo:

```text
02-configurar-dependencias-camunda.md
```

---

### 03 — Configuración básica

Revisar la configuración mínima necesaria para arrancar el motor.

Archivo:

```text
03-configuracion-basica.md
```

---

### 04 — Arranque del motor

Arrancar la aplicación y comprobar que el motor Camunda se inicia correctamente.

Archivo:

```text
04-arranque-del-motor.md
```

---

### 05 — Acceso a Cockpit

Acceder a la herramienta **Cockpit** para visualizar procesos y monitorizar el motor.

Archivo:

```text
05-acceso-cockpit.md
```

---

### 06 — Acceso a Tasklist

Acceder a **Tasklist**, la interfaz utilizada por los usuarios para completar tareas humanas.

Archivo:

```text
06-acceso-tasklist.md
```

---

# 🧪 Resultado esperado

Al finalizar este laboratorio:

* el motor Camunda estará funcionando dentro de la aplicación
* se podrá acceder a Cockpit y Tasklist
* la aplicación estará preparada para ejecutar procesos BPMN

---

# 🔜 Próximo laboratorio

En el siguiente laboratorio se comenzará a **modelar procesos BPMN más complejos** y a ejecutarlos dentro del motor.
