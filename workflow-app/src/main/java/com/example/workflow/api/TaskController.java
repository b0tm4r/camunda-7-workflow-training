package com.example.workflow.api;

// Imports de Camunda
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.User; // <--- EL QUE FALTABA

// Imports de Spring
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gestionar la ejecución (completado) de tareas.
 */
@RestController
@RequestMapping("/api/workflow")
public class TaskController {

    private final TaskService taskService;
    private final IdentityService identityService;

    public TaskController(TaskService taskService, IdentityService identityService) {
        this.taskService = taskService;
        this.identityService = identityService;
    }

    /**
     * Completa una tarea específica y le asigna un importe.
     * * @param taskId El ID de la tarea (se obtiene de la Tasklist o Cockpit)
     * 
     * @param userId  El usuario que está completando la tarea
     * @param importe El valor que queremos pasar al proceso
     * @return Mensaje de éxito
     */
    @PostMapping("/complete/{taskId}")
    public ResponseEntity<String> completeTask(
            @PathVariable String taskId,
            @RequestParam String userId,
            @RequestParam int importe) {

        try {

            // --- VALIDACIÓN DE USUARIO ---
            // Buscamos si el usuario existe en las tablas de identidad de Camunda
            User user = identityService.createUserQuery().userId(userId).singleResult();

            if (user == null) {
                // Si no existe, devolvemos un 404 o un error descriptivo
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: El usuario '" + userId + "' no existe en el sistema.");
            }

            // 2. Validar si el usuario pertenece al grupo "grupo"
            long count = identityService.createUserQuery()
                    .userId(userId)
                    .memberOfGroup("grupo") // <--- Filtro de grupo
                    .count();

            if (count == 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Error: El usuario '" + userId + "' no pertenece al grupo 'grupo'.");
            }

            // 1. Identificamos quién está operando para que quede en el histórico
            identityService.setAuthenticatedUserId(userId);

            // 2. Preparamos la variable del importe
            Map<String, Object> variables = new HashMap<>();
            variables.put("importe", importe);
            variables.put("usuarioValidador", userId);

            // 3. Completamos la tarea pasando las variables
            // Esto hará que el proceso avance al siguiente nodo
            taskService.complete(taskId, variables);

            return ResponseEntity.ok("Tarea completada con éxito por: " + userId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al completar la tarea: " + e.getMessage());
        } finally {
            // 4. Limpiamos autenticación
            identityService.clearAuthentication();
        }
    }
}