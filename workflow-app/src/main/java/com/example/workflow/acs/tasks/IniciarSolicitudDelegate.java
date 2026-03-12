package com.example.workflow.acs.tasks;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;


@Component("iniciarSolicitudDelegate")
public class IniciarSolicitudDelegate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String nombreTarea = delegateTask.getName();
        System.out.println("LOG: Task Listener ejecutado en la tarea: " + nombreTarea);

        // 1. Crear variable nivel proceso (usando el contexto de ejecución de la tarea)
        delegateTask.getExecution().setVariable("estado", "VAL000");

    }
}