package com.ray.study.smaple.sb.wf.activiti.service.impl;

import com.ray.study.smaple.sb.wf.activiti.service.WorkflowService;
import com.ray.study.smaple.sb.wf.activiti.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * description
 *
 * @author r.shi 2020/12/22 13:03
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@Disabled
public class WorkflowServiceImplTest {
    @Autowired
    WorkflowService workflowService;

    @Resource
    private SecurityUtil securityUtil;

    @Test
    public void createDeployment() {
        String resource = "processes/leave-approve.bpmn";
        String name = "请假申请单流程";
        String key = "leave";
        Deployment deployment = workflowService.createDeployment(resource, name, key);
        log.info("流程部署id:{},流程部署名称:{}, Key:{}", deployment.getId(), deployment.getName(), deployment.getKey());
    }

    @Test
    public void deleteDeployment() {
        String deploymentId = "9ae72982-4413-11eb-af7a-00d8614134ba";
        workflowService.deleteDeployment(deploymentId);
        listProcessDefinition();
    }

    @Test
    public void listProcessDefinition() {
        List<ProcessDefinition> processDefinitions = workflowService.listProcessDefinition();
        for (ProcessDefinition p : processDefinitions) {
            log.info("DeploymentId:{}, Name:{}, Key:{}, Version:{}, Description:{}", p.getDeploymentId(), p.getName(), p.getKey(), p.getVersion(), p.getDescription());
        }
    }

    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "leave";
        String user = "ZhangSan@qq.com";
        startProcessInstance(processDefinitionKey, user);
    }

    private ProcessInstance startProcessInstance(String processDefinitionKey, String user) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("user", user);
        ProcessInstance processInstance = workflowService.startProcessInstance(processDefinitionKey, variables);
        log.info("流程实例ID:{}, 流程定义ID:{}, 流程定义名称:{}", processInstance.getId(), processInstance.getProcessDefinitionId(), processInstance.getProcessDefinitionName());
        return processInstance;
    }

    @Test
    public void queryTaskByAssignee() {
        String processDefinitionKey = "leave";
        String assignee = "ZhangSan@qq.com";
        queryTaskByAssignee(processDefinitionKey, assignee);
    }

    private List<Task> queryTaskByAssignee(String processDefinitionKey, String assignee) {
        List<Task> taskList = workflowService.queryTaskByAssignee(processDefinitionKey, assignee);
        taskList.forEach(task -> log.info("taskId:{}, taskName:{}, taskCreateTime:{}, assignee:{}, processInstanceId:{}, processDefinitionId:{}, executionId:{}", task.getId(), task.getName(), task.getCreateTime(), task.getAssignee(), task.getProcessInstanceId(), task.getProcessDefinitionId(), task.getExecutionId()));
        return taskList;
    }

    @Test
    public void completeTask() {
        securityUtil.logInAs("LiSi@qq.com");
        String taskId = "2304616d-441a-11eb-bd84-00d8614134ba";
        String approve = "LiSi@qq.com";
        completeTask(taskId, approve);
    }

    private void completeTask(String taskId, String approve) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("approve", approve);
        workflowService.completeTask(taskId, variables);
    }

    private void completeTask(String taskId, int audit) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("audit", audit);
        workflowService.completeTask(taskId, variables);
    }

    @Test
    public void queryHistoryTask() {
        List<HistoricTaskInstance> historicTaskInstances = workflowService.queryHistoryTask(null);
        historicTaskInstances.forEach(hti -> log.info("活动Id:{}, 流程实例ID:{},活动名称：{}, 办理人：{}, 开始时间：{},结束时间：{} ", hti.getId(), hti.getProcessInstanceId(), hti.getName(), hti.getAssignee(), hti.getStartTime(), hti.getEndTime()));

    }

    @Test
    public void testAllProcess() {
        String processDefinitionKey = "leave";
        String user1 = "ZhangSan@qq.com";
        String approve = "LiSi@qq.com";

        // 在这之前需要先部署流程配置

        // 1.然后, 张三开启一个请假流程
        log.info("张三开启一个请假流程：{}",processDefinitionKey );
        startProcessInstance(processDefinitionKey, user1);

        // 2. 张三查询自己的流程
        log.info("张三查询自己的流程：{}",processDefinitionKey );
        List<Task> tasks = queryTaskByAssignee(processDefinitionKey, user1);

        // 3. 张三登录后，提交给领导李四审核
        securityUtil.logInAs("ZhangSan@qq.com");
        String taskId = tasks.get(0).getId();
        log.info("张三提交自己的流程给李四，taskId：{} ",taskId );
        completeTask(taskId, approve);

        // 4.领导李四查询自己的流程
        log.info("领导李四查询自己的流程：{}",processDefinitionKey );
        List<Task> tasks2 = queryTaskByAssignee(processDefinitionKey, approve);


        // 5.领导李四提交自己的流程
        securityUtil.logInAs("LiSi@qq.com");
        String taskId2 = tasks2.get(0).getId();
        log.info("领导李四提交自己的流程：{}",taskId2 );
        completeTask( taskId2, 1);

        // 6.查询历史流程
        queryHistoryTask();
    }


}