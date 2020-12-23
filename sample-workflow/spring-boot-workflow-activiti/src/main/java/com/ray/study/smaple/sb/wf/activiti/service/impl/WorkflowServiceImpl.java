package com.ray.study.smaple.sb.wf.activiti.service.impl;

import com.ray.study.smaple.sb.wf.activiti.service.WorkflowService;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author r.shi 2020/12/21 21:11
 */
@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;
    @Resource
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;


    @Override
    public Deployment createDeployment(String resource, String name, String key) {
        return repositoryService.createDeployment()
                //添加bpmn资源
                .addClasspathResource(resource)
                .name(name)
                .key(key)
                .deploy();
    }

    @Override
    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
    }

    @Override
    public List<ProcessDefinition> listProcessDefinition() {
        return repositoryService.createProcessDefinitionQuery().list();
    }


    @Override
    public ProcessInstance startProcessInstance(String processDefinitionKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }


    @Override
    public List<Task> queryTaskByAssignee(String processDefinitionKey,String assignee) {
       return  taskService.createTaskQuery()
               .processDefinitionKey(processDefinitionKey)
               .taskAssignee(assignee)
               .list();
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        //拾取任务
        // taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskId).build());
        //执行任务
        taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskId).withVariables(variables).build());
    }

    @Override
    public List<HistoricTaskInstance> queryHistoryTask(String processInstanceId) {
        // 历史相关Service
        return historyService
                // 创建历史活动实例查询
                .createHistoricTaskInstanceQuery()
                // 执行流程实例id
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .asc()
                .list();
    }
}
