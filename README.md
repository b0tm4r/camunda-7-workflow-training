# Camunda 7 Workflow Training

Este curso introduce **Camunda 7** desde una perspectiva práctica. A lo largo de la formación se construirá progresivamente una aplicación de workflow completa basada en **Spring Boot y BPMN**.

El curso está diseñado como una **cadena de laboratorios progresivos**. Cada laboratorio amplía la aplicación existente incorporando nuevos conceptos del motor Camunda.

El objetivo es comprender:

* qué es Camunda y qué problema resuelve
* cómo se modelan procesos usando BPMN
* cómo se integran procesos con aplicaciones Java
* cómo se despliegan y operan motores de workflow
* cómo se depuran, monitorizan y mantienen procesos en producción

---

# Temario del curso

El contenido del curso cubre los siguientes bloques formativos:

1. Introducción a **BPMN 2.0**, **DMN** y **CMMN**
2. Arquitectura de Camunda: motor **embebido** y **standalone**
3. Instalación de Camunda con **Spring Boot** o **Tomcat**
4. Modelado de procesos con **Camunda Modeler**
5. Definición de **tareas de usuario** y **tareas de servicio**
6. Gestión de **variables**, **formularios**, **expresiones** y **scripts**
7. Configuración de **eventos**, **gateways**, **listeners** y **subprocesos**
8. **Despliegue**, **versionado** y **migración** de procesos
9. Interacción con **API REST** y **Java API**
10. Gestión de **errores**, **timeouts** y **compensaciones**
11. Uso de las consolas **Cockpit**, **Tasklist** y **Admin**
12. Integración con **bases de datos**, **colas** y **servicios externos**
13. **Seguridad**, autenticación y autorización
14. **Testing**, **debugging** y **monitorización** de procesos

---

# Relación entre temario y prácticas

El aprendizaje se realiza mediante una serie de **laboratorios encadenados** que implementan los conceptos del temario sobre una misma aplicación.

Cada laboratorio introduce nuevas capacidades del motor Camunda.

```
labs/
 ├── lab00-entorno
 │     Preparación del entorno de desarrollo
 │
 ├── lab01-bpmn-introduccion
 │     Introducción a BPMN y primer proceso
 │
 ├── lab02-arquitectura-camunda
 │     Arquitectura del motor de procesos
 │
 ├── lab03-instalacion-motor
 │     Instalación de Camunda con Spring Boot
 │
 ├── lab04-modelado-procesos
 │     Diseño de procesos con Camunda Modeler
 │
 ├── lab05-service-user-tasks
 │     Implementación de Service Tasks y User Tasks
 │
 ├── lab06-variables-expresiones
 │     Variables de proceso, formularios y expresiones
 │
 ├── lab07-gateways-eventos
 │     Gateways, eventos y control de flujo
 │
 ├── lab08-despliegue-versionado
 │     Despliegue de procesos y gestión de versiones
 │
 ├── lab09-apis-rest-java
 │     Control de procesos mediante APIs
 │
 ├── lab10-errores-compensaciones
 │     Manejo de errores, incidentes y compensaciones
 │
 ├── lab11-operacion-ui
 │     Operación del motor usando Cockpit y Tasklist
 │
 ├── lab12-integraciones
 │     Integración con sistemas externos
 │
 ├── lab13-seguridad
 │     Gestión de usuarios, grupos y permisos
 │
 └── lab14-testing-monitorizacion
       Testing, debugging y monitorización de procesos
```

Cada laboratorio contiene:

* explicación conceptual del tema
* pasos prácticos detallados
* cambios en la aplicación
* comprobaciones del comportamiento del proceso

---

# Herramientas utilizadas

Durante el curso se utilizarán las siguientes herramientas:

* GitHub Codespaces
* Visual Studio Code
* Java 17
* Maven
* Spring Boot
* Camunda 7
* Camunda Modeler
* Docker

---

# Arquitectura del sistema de ejemplo

Durante el curso se construirá una aplicación basada en la siguiente arquitectura:

```
BPMN Model
      │
      ▼
Spring Boot Application
      │
      ▼
Camunda Process Engine
      │
      ▼
Database
```

El **motor Camunda** se encarga de gestionar el estado y ejecución de los procesos mientras que la **aplicación Java** implementa la lógica de negocio.

---

# Metodología del curso

El curso sigue un enfoque incremental:

1. Preparar el entorno de desarrollo
2. Diseñar procesos BPMN
3. Integrar procesos con código Java
4. Ejecutar procesos y gestionar tareas
5. Gestionar errores y eventos
6. Integrar servicios externos
7. Operar y monitorizar el motor de procesos

Cada laboratorio modifica la aplicación desarrollada previamente, de forma que el sistema evoluciona gradualmente durante todo el curso.

---

# Cómo seguir el curso

1. Abrir el repositorio en **GitHub Codespaces**
2. Acceder al directorio `labs/`
3. Ejecutar los laboratorios en orden
4. Implementar los cambios en la aplicación

Los laboratorios están diseñados para ser **completados de forma secuencial**, ya que cada uno depende del anterior.

---

# Resultado final

Al finalizar el curso se habrá construido una aplicación completa basada en Camunda que incluye:

* procesos BPMN ejecutables
* lógica de negocio en Java
* integración con APIs externas
* gestión de tareas humanas
* monitorización y operación del motor
* control de errores y versionado de procesos
