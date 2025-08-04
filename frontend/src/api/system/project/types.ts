export interface ProjectVO {
  /**
   * 
   */
  id: string | number;

  /**
   * 项目名称
   */
  name: string;

  /**
   * 项目编码
   */
  code: string;

  /**
   * 项目类型
   */
  type: string;

  /**
   * 立项时间
   */
  initiationDate: string;

  /**
   * 归属区域
   */
  deptId: string | number;

  /**
   * 交付时间
   */
  deliveryDate: string;

  /**
   * 项目状态
   */
  status: string;

  /**
   * 项目金额(万)
   */
  amount: number;

  /**
   * 项目进店
   */
  progress: number;

  /**
   * 
   */
  remark: string;

}

export interface ProjectForm extends BaseEntity {
  /**
   * 
   */
  id?: string | number;

  /**
   * 项目名称
   */
  name?: string;

  /**
   * 项目编码
   */
  code?: string;

  /**
   * 项目类型
   */
  type?: string;

  /**
   * 立项时间
   */
  initiationDate?: string;

  /**
   * 归属区域
   */
  deptId?: string | number;

  /**
   * 交付时间
   */
  deliveryDate?: string;

  /**
   * 项目状态
   */
  status?: string;

  /**
   * 项目金额(万)
   */
  amount?: number;

  /**
   * 项目进店
   */
  progress?: number;

  /**
   * 
   */
  remark?: string;

}

export interface ProjectQuery extends PageQuery {

  /**
   * 项目名称
   */
  name?: string;

  /**
   * 项目编码
   */
  code?: string;

  /**
   * 项目类型
   */
  type?: string;

  /**
   * 立项时间
   */
  initiationDate?: string;

  /**
   * 归属区域
   */
  deptId?: string | number;

  /**
   * 交付时间
   */
  deliveryDate?: string;

  /**
   * 项目状态
   */
  status?: string;

  /**
   * 项目金额(万)
   */
  amount?: number;

  /**
   * 项目进店
   */
  progress?: number;

    /**
     * 日期范围参数
     */
    params?: any;
}



