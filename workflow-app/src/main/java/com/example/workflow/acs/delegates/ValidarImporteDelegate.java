package com.example.workflow.acs.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component("validarImporteDelegate") // <--- ¡Esto es lo que busca el BPMN!
public class ValidarImporteDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(ValidarImporteDelegate.class.getName());
    
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("... Validando importe de la solicitud para el proceso: " + execution.getProcessInstanceId());

        // 1. Comprobamos si la variable existe para evitar errores
        Object importeObj = execution.getVariable("importe");

        if (importeObj == null) {
            // Si no existe el importe, marcamos un estado de error o pendiente
            LOGGER.warning("!!! Variable 'importe' no encontrada. Modificando estado a ERROR_VAR.");
            execution.setVariable("estado", "ERR003");
        } else {
            // 2. Si existe, realizamos la lógica habitual
            int valorActual = (int) importeObj;
            LOGGER.info("### Importe recibido: " + valorActual);
            
            // Establecemos el estado de éxito en la validación
            execution.setVariable("estado", "VAL003");
        }

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                + "activityName='" + execution.getCurrentActivityName() + "'"
                + ", activityId=" + execution.getCurrentActivityId()
                + ", processDefinitionId=" + execution.getProcessDefinitionId()
                + ", processInstanceId=" + execution.getProcessInstanceId()
                + ", businessKey=" + execution.getProcessBusinessKey()
                + ", executionId=" + execution.getId()
                + ", variables=" + execution.getVariables()
                + " \n\n");
                        
    }
}