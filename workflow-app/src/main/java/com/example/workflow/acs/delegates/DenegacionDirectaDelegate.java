package com.example.workflow.acs.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component("denegacionDirectaDelegate") // <--- ¡Esto es lo que busca el BPMN!
public class DenegacionDirectaDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(DenegacionDirectaDelegate.class.getName());
    
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("... denegacion directa de la solicitud para el proceso: " + execution.getProcessInstanceId());

        execution.setVariable("estado", "ERR004");

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