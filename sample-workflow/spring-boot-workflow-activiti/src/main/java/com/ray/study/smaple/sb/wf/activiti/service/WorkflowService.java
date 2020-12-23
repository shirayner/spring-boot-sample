package com.ray.study.smaple.sb.wf.activiti.service;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author r.shi 2020/12/21 21:11
 */
public interface WorkflowService {

    /**
     * 根据bpmn文件，创建一个新的流程
     */
    Deployment createDeployment(String resource, String name, String key);

    /**
     * 删除已有的流程
     */
    void deleteDeployment(String deploymentId);

    /**
     * 查询已有的流程
     */
    List<ProcessDefinition> listProcessDefinition();

    /**
     * 启动一个实例
     */
    ProcessInstance startProcessInstance(String processDefinitionKey, Map<String, Object> variables);

    /**
     * 传递参数、读取参数
     * 查询用户的任务列表
     */
    List<Task> queryTaskByAssignee(String processDefinitionKey, String assignee);

    /**
     * 完成任务
     */
    void completeTask(String taskId, Map<String, Object> variables);

    /**
     * 历史活动实例查询,参数也能查到
     */
    List<HistoricTaskInstance> queryHistoryTask(String processInstanceId);
}
