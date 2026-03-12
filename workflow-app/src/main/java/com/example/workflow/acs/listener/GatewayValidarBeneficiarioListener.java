package com.example.workflow.acs.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("gatewayValidarBeneficiarioListener")
public class GatewayValidarBeneficiarioListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        // Aquí usamos 'execution' (igual que en un JavaDelegate)
        String evento = execution.getEventName(); // "start", "end" o "take"        
        System.out.println("LOG: Execution Listener detectado! Evento: " + evento);
        
        execution.setVariable("estado", "VAL002");
    }
}

