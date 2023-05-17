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

import java.util.List;

/**
 * @author wmstart
 * @create 2023-05-16 23:34
 */
@SpringBootTest
public class ProcessTestDemo3 {
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
                .addClasspathResource("process/jiaban03.bpmn20.xml")
                .name("加班申请流程03")
                .deploy();

        System.out.println(deployment.getName()+"\n"+deployment.getId());
    }


    //启动流程实例
    @Test
    public void startProcessInstance(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban03");

        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getId());


    }

    //查询组任务
    @Test
    public void findGroupTaskList(){
        List<Task> list = taskService.createTaskQuery()
                .taskCandidateUser("tom01")
                .list();
        for(Task task:list){
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }


    //拾取组任务
    @Test
    public void claimTask(){
        Task task = taskService.createTaskQuery()
                .taskCandidateUser("tom01")
                .singleResult();
        if (task != null) {

            taskService.claim(task.getId(),"tom01");
            System.out.println("任务拾取完成");
        }
    }






    @Test
    //查询某人代办任务
    public void findTaskList(){
        String assign="tom01";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assign).list();
        for(Task task:list){
            System.out.println("流程实例id"+task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());

        }
    }


    //办理个人任务
    @Test
    public void completGroupTask(){
        Task task= taskService.createTaskQuery()
                .taskAssignee("tom01")
                .singleResult();
        taskService.complete(task.getId());
    }


    //归还组任务
}
