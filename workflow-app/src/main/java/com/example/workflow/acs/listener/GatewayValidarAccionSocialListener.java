package com.example.workflow.acs.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component("gatewayValidarAccionSocialListener")
public class GatewayValidarAccionSocialListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        // Aquí usamos 'execution' (igual que en un JavaDelegate)
        String evento = execution.getEventName(); // "start", "end" o "take"
        System.out.println("LOG: Execution Listener detectado! Evento: " + evento);

        // 1. Obtener el valor actual (con cast a String para comparar)
        String valorActual = (String) execution.getVariable("estado");

        // 2. Definir los valores que queremos ignorar (los que NO deben cambiar el
        // estado)
        List<String> estadosProhibidos = Arrays.asList("VAL004", "ERR002", "ERR003", "ERR004");

        // 3. Comprobar: Si el valor actual NO está en la lista, entonces asignamos el
        // nuevo
        if (valorActual == null || !estadosProhibidos.contains(valorActual)) {
            System.out.println("LOG: El estado actual (" + valorActual + ") permite el cambio. Asignando VAL002.");
            execution.setVariable("estado", "ERR004");
        } else {
            System.out.println("LOG: No se cambia el estado porque el valor actual es: " + valorActual);
        }

    }
}
