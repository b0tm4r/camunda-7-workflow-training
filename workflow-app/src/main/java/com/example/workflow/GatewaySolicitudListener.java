package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("gatewaySolicitudListener")
public class GatewaySolicitudListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        // Aquí usamos 'execution' (igual que en un JavaDelegate)
        String evento = execution.getEventName(); // "start", "end" o "take"
        
        System.out.println("LOG: Execution Listener detectado! Evento: " + evento);
        
        // Ejemplo: Crear una variable de auditoría
        execution.setVariable("decision", "APROBADO");
    }
}

