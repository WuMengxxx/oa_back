package com.wumeng.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wmstart
 * @create 2023-05-16 19:24
 */
@SpringBootTest
public class ProcessTest1 {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    //部署流程定义
    @Test
    public void deplayProcess(){
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/jiaban02.bpmn20.xml")
                .name("加班申请流程02")
                .deploy();

        System.out.println(deployment.getName()+"\n"+deployment.getId());
    }

    //启动流程实例
    @Test
    public void startProcessInstance(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban02");

        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getId());


    }

    @Test
    //查询某人代办任务
    public void findTaskList(){
        String assign="jack";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assign).list();
        for(Task task:list){
            System.out.println("流程实例id"+task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());

        }
    }



}
