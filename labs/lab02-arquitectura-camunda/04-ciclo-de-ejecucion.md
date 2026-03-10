# Ciclo de ejecución de un proceso

## 🎯 Objetivo

Observar cómo el motor Camunda ejecuta un proceso BPMN iniciando una instancia de proceso desde la aplicación.

**Requisito:** Necesitas tener **Camunda instalado** (lab03) y al menos un proceso BPMN desplegado. Si aún no has llegado a ese punto, completa el lab03 y el lab de despliegue y vuelve aquí.

---

## 🧠 Contexto

Cuando un proceso BPMN se ejecuta en Camunda ocurre una secuencia de pasos internos:

1. el proceso se **despliega**
2. el motor crea una **instancia del proceso**
3. el motor ejecuta las **actividades del modelo**
4. el proceso avanza hasta llegar al **evento de fin**

En este ejercicio vamos a **iniciar una instancia de proceso y observar su ejecución**.

---

# Verificar que existe un proceso BPMN

En el **explorador** de VS Code, dentro de **workflow-app**, entra en **src** → **main** → **resources** → **processes** (si no existe la carpeta processes, créala al copiar un BPMN en el lab de despliegue). Comprueba que hay al menos un archivo BPMN, por ejemplo **approval.bpmn** o **approval-process.bpmn**.

---

# Verificar que el proceso es ejecutable

Abre ese archivo BPMN (clic en el explorador o **Ctrl+P** y el nombre del archivo). En el contenido XML comprueba que aparece algo como:

```xml id="8ukvmb"
<bpmn:process id="approval" isExecutable="true">
```

Esto indica que el proceso puede ser ejecutado por el motor.

---

# Iniciar una instancia de proceso

Abrir la clase principal de la aplicación:

```id="7cqf1m"
WorkflowAppApplication.java
```

Si todavía no tienes código que inicie el proceso, añade el siguiente `CommandLineRunner`:

```java id="p0s1pq"
@Bean
public CommandLineRunner startProcess(RuntimeService runtimeService) {
    return args -> {
        runtimeService.startProcessInstanceByKey("approval-process");
        System.out.println(">>> Proceso arrancado automáticamente");
    };
}
```

Sustituye `"approval-process"` por el **id del proceso** que hayas visto en el XML (por ejemplo `"approval"`).  
Este método indica al motor que debe **crear una nueva instancia del proceso** cuando la aplicación arranca.

---

# Ejecutar la aplicación

Arrancar la aplicación:

```bash id="j29os1"
mvn spring-boot:run
```

Cuando la aplicación arranque, el proceso se iniciará automáticamente.

---

# Observar la ejecución

Durante la ejecución el motor realiza internamente varias acciones:

```id="jq61ey"
1. localiza el proceso BPMN
2. crea una instancia del proceso
3. avanza por los elementos del diagrama
4. finaliza el proceso
```

Estas acciones forman parte del **ciclo de ejecución del motor**.

---

# Verificar en la base de datos

El motor guarda el estado de los procesos en la base de datos.

En este proyecto se utiliza:

```id="y0a3g2"
H2
```

Por lo tanto cada instancia de proceso queda registrada internamente en el motor.

---

# Resumen del ciclo de ejecución

El flujo de ejecución de Camunda puede representarse de la siguiente forma:

```id="pn0cym"
BPMN Model
     │
     ▼
Process Definition (despliegue)
     │
     ▼
Process Instance (ejecución)
     │
     ▼
Actividades del proceso
     │
     ▼
Evento de fin
```

---

# Comprobación

El ejercicio se considera completado cuando:

* se identifica el archivo BPMN del proceso
* se localiza el código que inicia el proceso
* se arranca la aplicación
* se comprende el ciclo de ejecución del motor Camunda.
