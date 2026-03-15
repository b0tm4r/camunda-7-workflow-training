package com.example.workflow.solicitudes.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.workflow.solicitudes.dto.ProcesoInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar y consultar el estado de las solicitudes
 * en el motor de flujos Camunda 7.
 */
@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * Obtiene información detallada de una instancia de proceso, incluyendo
     * los nombres legibles de las actividades actuales y todas sus variables.
     * * @param businessKey Clave de negocio única de la solicitud.
     * @return Lista de objetos ProcesoInfo con el detalle técnico y de negocio.
     */
    @GetMapping("/buscar/detalle/{businessKey}")
    public ResponseEntity<List<ProcesoInfo>> buscarDetallePorBusinessKey(@PathVariable String businessKey) {
        try {
            // 1. Buscamos las instancias activas asociadas a la clave de negocio
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                    .processInstanceBusinessKey(businessKey);

            List<ProcessInstance> instancias = query.list();

            List<ProcesoInfo> detalles = instancias.stream().map(instancia -> {
                String instanceId = instancia.getId();

                // 2. Recuperamos los IDs técnicos (ej: "UserTask_1") de donde se encuentra el flujo
                List<String> actividadIds = runtimeService.getActiveActivityIds(instanceId);

                // 3. Cargamos el modelo BPMN para convertir IDs técnicos en nombres amigables (ej: "Aprobar Solicitud")
                BpmnModelInstance modelInstance = repositoryService
                        .getBpmnModelInstance(instancia.getProcessDefinitionId());

                List<String> nombresActividades = actividadIds.stream().map(id -> {
                    FlowNode flowNode = modelInstance.getModelElementById(id);
                    return (flowNode != null && flowNode.getName() != null) ? flowNode.getName() : id;
                }).collect(Collectors.toList());

                // 4. Recuperamos el mapa completo de variables de proceso actuales
                Map<String, Object> variables = runtimeService.getVariables(instanceId);

                return new ProcesoInfo(instanceId, instancia.getBusinessKey(), nombresActividades, variables);
            }).collect(Collectors.toList());

            return ResponseEntity.ok(detalles);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Busca y retorna los IDs internos de Camunda para un proceso específico 
     * filtrado por su clave de negocio.
     * * @param businessKey Identificador de negocio de la solicitud.
     * @return Lista de IDs de instancia de proceso (UUIDs de Camunda).
     */
    @GetMapping("/buscar/{businessKey}")
    public ResponseEntity<List<String>> buscarPorBusinessKey(@PathVariable String businessKey) {
        try {
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey("Process_1xvj5rk")
                    .processInstanceBusinessKey(businessKey);

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

    /**
     * Lista todas las Business Keys de los procesos activos. 
     * Permite filtrar opcionalmente por el valor de una variable de proceso.
     * * @param nombreVar (Opcional) Nombre de la variable a filtrar.
     * @param valorVar (Opcional) Valor esperado de la variable.
     * @return Lista de claves de negocio encontradas.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<String>> listarBusinessKeys(
            @RequestParam(required = false) String nombreVar,
            @RequestParam(required = false) String valorVar) {

        try {
            ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey("Process_1xvj5rk")
                    .active();

            // Filtro dinámico: solo si se proporcionan ambos parámetros de variable
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

    /**
     * Envía una señal de correlación de mensaje para avanzar o cancelar 
     * un flujo que esté esperando el evento 'cancelar_tarea_msg'.
     * * @param businessKey Clave de negocio de la instancia a la que enviar el mensaje.
     * @return Mensaje de confirmación o error de correlación.
     */
    @PostMapping("/{businessKey}/cancelar")
    public ResponseEntity<String> cancelarTarea(@PathVariable String businessKey) {
        try {
            // Correlaciona el mensaje BPMN con la instancia específica basada en la Business Key
            runtimeService.createMessageCorrelation("cancelar_tarea_msg")
                    .processInstanceBusinessKey(businessKey)
                    .correlate();

            return ResponseEntity.ok("Mensaje de cancelación enviado correctamente para: " + businessKey);

        } catch (org.camunda.bpm.engine.MismatchingMessageCorrelationException e) {
            // Se lanza si no hay ninguna instancia esperando ese mensaje específico en ese momento
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una instancia esperando cancelación para la clave: " + businessKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la cancelación: " + e.getMessage());
        }
    }
}