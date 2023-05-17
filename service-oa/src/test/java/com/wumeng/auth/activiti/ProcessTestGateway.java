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
 * @create 2023-05-17 15:34
 */
@SpringBootTest
public class ProcessTestGateway {

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
                .addClasspathResource("process/qingjia003.bpmn20.xml")
                .name("请假申请003")
                .deploy();

        System.out.println(deployment.getName()+"\n"+deployment.getId());
    }


    //启动流程实例
    @Test
    public void startProcessInstance(){


        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia003");

        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getId());


    }

    //查询组任务
    @Test
    public void findTaskList(){
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("wangwu")
                .list();
        for(Task task:list){
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }
    @Test
    public void findTaskList1(){
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("gouwa")
                .list();
        for(Task task:list){
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }

    //办理个人任务
    @Test
    public void completGroupTask(){
        Task task= taskService.createTaskQuery()
                .taskAssignee("wangwu")
                .singleResult();
        taskService.complete(task.getId());
    }
    @Test
    public void completGroupTask1(){
        Task task= taskService.createTaskQuery()
                .taskAssignee("gouwa")
                .singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void findTaskList2(){
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("xiaoli")
                .list();
        for(Task task:list){
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }
}
