package com.example.workflow.api;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.workflow.api.dto.ProcesoInfo;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService; // Necesario para leer el nombre del BPMN

    @GetMapping("/buscar/detalle/{businessKey}")
    public ResponseEntity<List<ProcesoInfo>> buscarDetallePorBusinessKey(@PathVariable String businessKey) {
        try {
            // 1. Buscamos las instancias por Business Key
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(businessKey);

            List<ProcessInstance> instancias = query.list();

            List<ProcesoInfo> detalles = instancias.stream().map(instancia -> {
                String instanceId = instancia.getId();

                // 2. Obtenemos los IDs de las actividades activas
                List<String> actividadIds = runtimeService.getActiveActivityIds(instanceId);

                // 3. Obtenemos el modelo para traducir IDs a Nombres
                BpmnModelInstance modelInstance = repositoryService
                        .getBpmnModelInstance(instancia.getProcessDefinitionId());

                List<String> nombresActividades = actividadIds.stream().map(id -> {
                    FlowNode flowNode = modelInstance.getModelElementById(id);
                    return (flowNode != null && flowNode.getName() != null) ? flowNode.getName() : id;
                }).collect(Collectors.toList());

                // 4. NUEVO: Obtenemos todas las variables de la instancia
                Map<String, Object> variables = runtimeService.getVariables(instanceId);

                return new ProcesoInfo(instanceId, instancia.getBusinessKey(), nombresActividades, variables);
            }).collect(Collectors.toList());

            return ResponseEntity.ok(detalles);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * Busca todas las instancias (activas o suspendidas) que tengan una Business
     * Key específica.
     * GET /api/solicitudes/buscar/123
     */
    @GetMapping("/buscar/{businessKey}")
    public ResponseEntity<List<String>> buscarPorBusinessKey(@PathVariable String businessKey) {
        try {
            // Creamos la consulta filtrando por la clave de negocio
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey("Process_1xvj5rk")
                    .processInstanceBusinessKey(businessKey);

            // Obtenemos la lista de instancias y extraemos sus IDs
            List<String> instanceIds = query.list().stream()
                    .map(ProcessInstance::getId)
                    .collect(Collectors.toList());

            if (instanceIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(instanceIds);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<String>> listarBusinessKeys(
            @RequestParam(required = false) String nombreVar,
            @RequestParam(required = false) String valorVar) {

        try {
            // Ahora el compilador reconocerá ProcessInstanceQuery
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey("Process_1xvj5rk")
                    .active();

            if (nombreVar != null && !nombreVar.isEmpty() && valorVar != null) {
                query.variableValueEquals(nombreVar, valorVar);
            }

            List<String> businessKeys = query.list().stream()
                    .map(ProcessInstance::getBusinessKey)
                    .filter(bk -> bk != null)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(businessKeys);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{businessKey}/cancelar")
    public ResponseEntity<String> cancelarTarea(@PathVariable String businessKey) {
        try {
            runtimeService.createMessageCorrelation("cancelar_tarea_msg")
                    .processInstanceBusinessKey(businessKey)
                    .correlate();

            return ResponseEntity.ok("Mensaje de cancelación enviado correctamente para: " + businessKey);

        } catch (org.camunda.bpm.engine.MismatchingMessageCorrelationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una instancia esperando cancelación para la clave: " + businessKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la cancelación: " + e.getMessage());
        }
    }
}