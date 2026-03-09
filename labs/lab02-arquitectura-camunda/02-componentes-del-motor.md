# Explorar componentes del motor Camunda desde código

## 🎯 Objetivo

Acceder al **Process Engine de Camunda desde código Java** para explorar los servicios disponibles del motor.

**Requisito:** Este paso requiere tener ya **Camunda instalado** en el proyecto (lab03, paso 02). Si aún no has añadido la dependencia, ve al **lab03**, añade el motor y las dependencias, y luego vuelve aquí.

---

## 🧠 Contexto

En aplicaciones Spring Boot con Camunda, el **Process Engine se crea automáticamente** cuando arranca la aplicación.

El motor expone varios **servicios internos** que permiten interactuar con los procesos:

* RuntimeService
* TaskService
* RepositoryService
* HistoryService
* ManagementService

En este ejercicio vamos a **obtener estos servicios desde código y mostrarlos al arrancar la aplicación**.

---

# Abrir la clase principal de la aplicación

Ir al archivo:

```id="k9zgrd"
src/main/java/com/example/workflow/WorkflowAppApplication.java
```

---

# Modificar el CommandLineRunner

Localizar el método donde se inicia el proceso.

Modificar el método para obtener referencias al motor y a sus servicios.

Ejemplo:

```java id="i1s9tg"
@Bean
public CommandLineRunner inspectEngine(ProcessEngine processEngine) {
    return args -> {

        System.out.println("Process Engine Name: " + processEngine.getName());

        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        HistoryService historyService = processEngine.getHistoryService();
        ManagementService managementService = processEngine.getManagementService();

        System.out.println("RuntimeService: " + runtimeService);
        System.out.println("TaskService: " + taskService);
        System.out.println("RepositoryService: " + repositoryService);
        System.out.println("HistoryService: " + historyService);
        System.out.println("ManagementService: " + managementService);

    };
}
```

---

## 📦 Importaciones necesarias

Para que el código anterior compile sin errores en tu clase `WorkflowAppApplication`, asegúrate de tener estas importaciones:

```java
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.RepositoryService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
```

---

# Ejecutar la aplicación

Desde el directorio `workflow-app` ejecutar:

```bash id="8sl18c"
mvn spring-boot:run
```

---

# Observar la salida en la consola

Durante el arranque deberían aparecer mensajes similares a:

```
Process Engine Name: default
RuntimeService: org.camunda.bpm.engine.impl.RuntimeServiceImpl
TaskService: org.camunda.bpm.engine.impl.TaskServiceImpl
RepositoryService: org.camunda.bpm.engine.impl.RepositoryServiceImpl
HistoryService: org.camunda.bpm.engine.impl.HistoryServiceImpl
ManagementService: org.camunda.bpm.engine.impl.ManagementServiceImpl
```

Esto confirma que el **Process Engine está disponible y que sus servicios pueden utilizarse desde código**.

---

# Qué hemos observado

El **Process Engine** actúa como punto central del motor Camunda.

A partir de él se obtienen distintos servicios especializados:

```id="ksubm0"
ProcessEngine
   │
   ├── RuntimeService
   ├── TaskService
   ├── RepositoryService
   ├── HistoryService
   └── ManagementService
```

Cada servicio permite interactuar con una parte diferente del sistema de procesos.

---

# Comprobación

El ejercicio se considera completado cuando:

* la aplicación arranca correctamente
* se obtiene el **ProcessEngine desde Spring**
* se muestran en consola los servicios disponibles del motor.
