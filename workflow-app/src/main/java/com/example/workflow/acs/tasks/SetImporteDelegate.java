package com.example.workflow.acs.tasks;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import com.example.workflow.BusinessRuleException;

@Component("setImporteDelegate")
public class SetImporteDelegate implements TaskListener {
    private final Logger LOGGER = Logger.getLogger(SetImporteDelegate.class.getName());

    @Override
    public void notify(DelegateTask delegateTask) {
        String nombreTarea = delegateTask.getName();
        System.out.println("LOG: Task Listener ejecutado en la tarea: " + nombreTarea);

        // IMPORTANTE: En un TaskListener usamos 'delegateTask' para acceder a las variables
        Object importeObj = delegateTask.getVariable("importe");

        if (importeObj == null) {
            LOGGER.warning("!!! Variable 'importe' no encontrada.");
            throw new BusinessRuleException("IMPORTE_INSUFICIENTE", "no hay importe");            
        } else {
            // Conversión segura de la variable
            int valorActual = Integer.parseInt(importeObj.toString());
            LOGGER.info("### Importe recibido: " + valorActual);
            
            if (valorActual < 100) {
                // Esta es la excepción que capturará tu @ControllerAdvice
                throw new BusinessRuleException("IMPORTE_INSUFICIENTE", "El importe debe ser mayor de 100.");
            }           
        }

    }
}