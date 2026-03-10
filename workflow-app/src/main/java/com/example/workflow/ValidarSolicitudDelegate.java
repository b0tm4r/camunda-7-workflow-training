package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component("validarSolicitudDelegate") // <--- ¡Esto es lo que busca el BPMN!
public class ValidarSolicitudDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(ValidarSolicitudDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("... Validando la solicitud para el proceso: " + execution.getProcessInstanceId());
        
        // Aquí podrías añadir lógica, por ejemplo:
        execution.setVariable("esValida", true);
    }
}