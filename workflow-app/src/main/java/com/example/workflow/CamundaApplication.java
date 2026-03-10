package com.example.workflow;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableProcessApplication("workflow-app")
public class CamundaApplication {

  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }

  @Bean
  public CommandLineRunner starProcess(RuntimeService runtimeService) {
    return args -> {
      runtimeService.startProcessInstanceByKey("approval-process");
       System.out.println(">>> Proceso arrancado automaticamente");
    };
  }

  @Bean
  public CommandLineRunner inspectEngine(ProcessEngine processEngine) {
    return args -> {

      System.out.println("Process Engine Name: " + processEngine.getName());

      RuntimeService runtimeService = processEngine.getRuntimeService();
      TaskService taskService = processEngine.getTaskService();
      RepositoryService repositoryService = processEngine.getRepositoryService();
      HistoryService historyService = processEngine.getHistoryService();
      ManagementService managementService = processEngine.getManagementService();

      System.out.println("RuntimeService: " + runtimeService);
      System.out.println("TaskService: " + taskService);
      System.out.println("RepositoryService: " + repositoryService);
      System.out.println("HistoryService: " + historyService);
      System.out.println("ManagementService: " + managementService);

    };
  }

}
