import request from '@/utils/request';

export interface WorkflowStartParams {
  flowCode: string;
  variables: Record<string, any>;
}

export interface WorkflowTaskParams {
  instanceId: string;
  nodeCode: string;
  formData?: Record<string, any>;
  opinion?: string;
  attachment?: string;
  comment?: string;
}

// 启动工作流
export function startWorkflow(data: WorkflowStartParams) {
  return request({
    url: '/system/workflow/start',
    method: 'post',
    data: data
  });
}

// 提交工作流任务
export function submitWorkflowTask(data: WorkflowTaskParams) {
  return request({
    url: '/system/workflow/task/submit',
    method: 'post',
    data: data
  });
}

// 审核通过
export function approveWorkflowTask(data: WorkflowTaskParams) {
  return request({
    url: '/system/workflow/task/approve',
    method: 'post',
    data: data
  });
}

// 驳回任务
export function rejectWorkflowTask(data: WorkflowTaskParams) {
  return request({
    url: '/system/workflow/task/reject',
    method: 'post',
    data: data
  });
}

// 完成任务
export function completeWorkflowTask(data: WorkflowTaskParams) {
  return request({
    url: '/system/workflow/task/complete',
    method: 'post',
    data: data
  });
}

// 获取工作流实例详情
export function getWorkflowInstance(instanceId: string) {
  return request({
    url: `/system/workflow/instance/${instanceId}`,
    method: 'get'
  });
}

// 获取待办任务列表
export function getTodoTasks(query: any) {
  return request({
    url: '/system/workflow/task/todo',
    method: 'get',
    params: query
  });
}

// 获取工作流历史记录
export function getWorkflowHistory(instanceId: string) {
  return request({
    url: `/system/workflow/history/${instanceId}`,
    method: 'get'
  });
}

// 获取工作流任务变量
export function getWorkflowTaskVariables(taskId: string) {
  return request({
    url: `/system/workflow/task/${taskId}/variables`,
    method: 'get'
  });
}