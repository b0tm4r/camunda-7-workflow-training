package com.example.workflow.acs.tasks;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;


@Component("aceptarProcesoDelegate")
public class AceptarProcesoDelegate implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String nombreTarea = delegateTask.getName();
        System.out.println("LOG: Task Listener ejecutado en la tarea: " + nombreTarea);
    }
}