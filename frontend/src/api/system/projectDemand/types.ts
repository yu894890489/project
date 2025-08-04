export interface ProjectDemandVO {
  /**
   * 
   */
  id: string | number;

  /**
   * 
   */
  projectId: string | number;

  /**
   * 
   */
  deptId: string | number;

  /**
   * 需求名称
   */
  name: string;

  /**
   * 项目名称
   */
  defineProjectName: string;

  /**
   * 产品情况
   */
  productSituation: string;

  /**
   * 批次名称
   */
  title: string;

  /**
   * 需求状态
   */
  status: string;

  /**
   * 需求类型
   */
  type: string;

  /**
   * 预估成本(万)
   */
  cost: number;

  /**
   * 开始时间
   */
  startTime: string;

  /**
   * 交付时间
   */
  deliveryTime: string;

  /**
   * 期望交付时间
   */
  expectDeliveryTime: string;

  /**
   * 签约情况 1是0否
   */
  signingStatus: number;

  /**
   * 需求确认状态
   */
  confirmStatus: number;

  /**
   * 
   */
  developmentStatus: number;

  /**
   * 设计状态
   */
  designStatus: number;

  /**
   * 
   */
  testStatus: number;

  /**
   * 交付状态
   */
  deliveryStatus: number;

  /**
   * 运维状态
   */
  operationStatus: number;

  /**
   * 需求附件 
   */
  attachment: string;

  /**
   * 审核人
   */
  auditUserId: string | number;

  /**
   * 审核已经
   */
  auditOpinion: string;

  /**
   * 审核反馈附件
   */
  auditAttachment: string;

  /**
   * 进度
   */
  progress: number;

  /**
   * 需求描述
   */
  remark: string;

  /**
   * 工作流实例ID
   */
  workflowInstanceId?: string;

  /**
   * 工作流状态 0-未启动 1-已启动
   */
  workflowStatus?: number;

}

export interface ProjectDemandForm extends BaseEntity {
  /**
   * 
   */
  id?: string | number;

  /**
   * 
   */
  projectId?: string | number;

  /**
   * 
   */
  deptId?: string | number;

  /**
   * 需求名称
   */
  name?: string;

  /**
   * 项目名称
   */
  defineProjectName?: string;

  /**
   * 产品情况
   */
  productSituation?: string;

  /**
   * 批次名称
   */
  title?: string;

  /**
   * 需求状态
   */
  status?: string;

  /**
   * 需求类型
   */
  type?: string;

  /**
   * 预估成本(万)
   */
  cost?: number;

  /**
   * 开始时间
   */
  startTime?: string;

  /**
   * 交付时间
   */
  deliveryTime?: string;

  /**
   * 期望交付时间
   */
  expectDeliveryTime?: string;

  /**
   * 签约情况 1是0否
   */
  signingStatus?: number;

  /**
   * 需求确认状态
   */
  confirmStatus?: number;

  /**
   * 
   */
  developmentStatus?: number;

  /**
   * 设计状态
   */
  designStatus?: number;

  /**
   * 
   */
  testStatus?: number;

  /**
   * 交付状态
   */
  deliveryStatus?: number;

  /**
   * 运维状态
   */
  operationStatus?: number;

  /**
   * 需求附件 
   */
  attachment?: string;

  /**
   * 审核人
   */
  auditUserId?: string | number;

  /**
   * 审核已经
   */
  auditOpinion?: string;

  /**
   * 审核反馈附件
   */
  auditAttachment?: string;

  /**
   * 进度
   */
  progress?: number;

  /**
   * 需求描述
   */
  remark?: string;

  /**
   * 工作流实例ID
   */
  workflowInstanceId?: string;

  /**
   * 工作流状态 0-未启动 1-已启动
   */
  workflowStatus?: number;

}

export interface ProjectDemandQuery extends PageQuery {

  /**
   * 
   */
  projectId?: string | number;

  /**
   * 
   */
  deptId?: string | number;

  /**
   * 需求名称
   */
  name?: string;

  /**
   * 项目名称
   */
  defineProjectName?: string;

  /**
   * 产品情况
   */
  productSituation?: string;

  /**
   * 批次名称
   */
  title?: string;

  /**
   * 需求状态
   */
  status?: string;

  /**
   * 需求类型
   */
  type?: string;

  /**
   * 预估成本(万)
   */
  cost?: number;

  /**
   * 开始时间
   */
  startTime?: string;

  /**
   * 交付时间
   */
  deliveryTime?: string;

  /**
   * 期望交付时间
   */
  expectDeliveryTime?: string;

  /**
   * 签约情况 1是0否
   */
  signingStatus?: number;

  /**
   * 需求确认状态
   */
  confirmStatus?: number;

  /**
   * 
   */
  developmentStatus?: number;

  /**
   * 设计状态
   */
  designStatus?: number;

  /**
   * 
   */
  testStatus?: number;

  /**
   * 交付状态
   */
  deliveryStatus?: number;

  /**
   * 运维状态
   */
  operationStatus?: number;

  /**
   * 需求附件 
   */
  attachment?: string;

  /**
   * 审核人
   */
  auditUserId?: string | number;

  /**
   * 审核已经
   */
  auditOpinion?: string;

  /**
   * 审核反馈附件
   */
  auditAttachment?: string;

  /**
   * 进度
   */
  progress?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



