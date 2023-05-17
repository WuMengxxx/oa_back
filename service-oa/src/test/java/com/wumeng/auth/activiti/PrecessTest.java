package com.wumeng.auth.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wmstart
 * @create 2023-05-15 20:36
 */

@SpringBootTest
public class PrecessTest {


    //注入RepositoryService
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;



    //单个流程实例挂起
    @Test
    public void SingleSuspendProcessInstance(){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("0f35ca17-f3ca-11ed-b427-145afc1e0d5f")
                .singleResult();
        boolean suspended = processInstance.isSuspended();
        if(suspended){
            //激活
            runtimeService.activateProcessInstanceById("0f35ca17-f3ca-11ed-b427-145afc1e0d5f");
            System.out.println(processInstance+"激活");
        }else {
            runtimeService.suspendProcessInstanceById("0f35ca17-f3ca-11ed-b427-145afc1e0d5f");
            System.out.println(processInstance+"挂起");
        }
    }

    //全部流程实例进行挂起
    @Test
    public void suspendProcessInstanceAll(){
        //获取流程定义对象
        ProcessDefinition qingjia = repositoryService.createProcessDefinitionQuery().processDefinitionKey("请假")
                .singleResult();


        //调用对象里的方法判断状态
        boolean suspended = qingjia.isSuspended();

        //判断如果挂起，实现激活
        if (suspended) {
            //流程定义id
            //是否激活true
            //时间点
            repositoryService.activateProcessDefinitionById(qingjia.getId(),
                    true,null);

            System.out.println(qingjia.getId()+"激活了");
        }  else {
            repositoryService.suspendProcessDefinitionById(qingjia.getId(),
                    true,null);

            System.out.println(qingjia.getId()+"挂起了");

        }



        //如果
    }



    //创建流程实例，指定BusinessKey

    @Test
    public void startUpProcessAddBusinessKey(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("请假", "1001");
        System.out.println(processInstance.getBusinessKey());
        System.out.println(processInstance.getId());
    }


    //启动流程实例
    @Test
    public void startProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("请假");
        System.out.println("流程定义id"+processInstance.getProcessDefinitionId());
        System.out.println("流程实例id"+processInstance.getId());
        System.out.println("流程活动id"+processInstance.getActivityId());

    }


    @Test
    //查询某人代办任务
    public void findTaskList(){
        String assign="lucy";
        List<Task> list = taskService.createTaskQuery().taskAssignee("lucy").list();
        for(Task task:list){
            System.out.println("流程实例id"+task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());

        }
    }


    @Test
    public void completeTask(){
        //查询负责人需要处理的任务
        Task task = taskService.createTaskQuery().taskAssignee("张三").singleResult();
        //完成任务,参数是任务id
        taskService.complete(task.getId());

    }


    //查询已处理任务
    @Test
    public void findCompleteTaskList(){
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .taskAssignee("李四")
                .finished().list();


        for(HistoricTaskInstance historicTaskInstance:list){
            System.out.println("实例id"+historicTaskInstance.getId());
            System.out.println("任务id：" + historicTaskInstance.getId());
            System.out.println("任务负责人：" + historicTaskInstance.getAssignee());
            System.out.println("任务名称：" + historicTaskInstance.getName());
        }

    }




    @Test
    public void deployProcess(){
        //流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .addClasspathResource("process/qingjia.png")
                .name("请假申请流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());

    }



}
