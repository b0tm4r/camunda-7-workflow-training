package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;


@Component("aprobarSolicitudDelegate")
public class AprobarSolicitudDelegate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String nombreTarea = delegateTask.getName();

        // 1. Crear variable nivel proceso (usando el contexto de ejecución de la tarea)
        delegateTask.getExecution().setVariable("notificado", true);

        System.out.println("LOG: Task Listener ejecutado en la tarea: " + nombreTarea);
        
        // 2. Lanzar la señal
        // IMPORTANTE: Cambiamos 'execution' por 'delegateTask.getExecution()'
        delegateTask.getExecution().getProcessEngineServices().getRuntimeService()
                .createSignalEvent("solicitudAprobada")
                .setVariables(delegateTask.getExecution().getVariables()) // <--- AQUÍ ESTABA EL ERROR
                .send();
    }
}