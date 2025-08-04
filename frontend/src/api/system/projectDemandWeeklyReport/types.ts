export interface ProjectDemandWeeklyReportVO {
  /**
   * 
   */
  id: string | number;

  /**
   * 标题
   */
  title: string;

  /**
   * 需求id
   */
  projectDemandId: string | number;

  /**
   * 填报日期
   */
  fllingDate: string;

  /**
   * 内容
   */
  content: string;

  /**
   * 附件
   */
  attachment: string;

}

export interface ProjectDemandWeeklyReportForm extends BaseEntity {
  /**
   * 
   */
  id?: string | number;

  /**
   * 标题
   */
  title?: string;

  /**
   * 需求id
   */
  projectDemandId?: string | number;

  /**
   * 填报日期
   */
  fllingDate?: string;

  /**
   * 内容
   */
  content?: string;

  /**
   * 附件
   */
  attachment?: string;

}

export interface ProjectDemandWeeklyReportQuery extends PageQuery {

  /**
   * 标题
   */
  title?: string;

  /**
   * 需求id
   */
  projectDemandId?: string | number;

  /**
   * 填报日期
   */
  fllingDate?: string;

  /**
   * 内容
   */
  content?: string;

  /**
   * 附件
   */
  attachment?: string;

    /**
     * 日期范围参数
     */
    params?: any;
}



