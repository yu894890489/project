package org.dromara.project.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 需求管理对象 tower_project_demand
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tower_project_demand")
public class TowerProjectDemand extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     *
     */
    private Long projectId;

    /**
     *
     */
    private Long deptId;

    /**
     * 需求名称
     */
    private String name;

    /**
     * 项目名称
     */
    private String defineProjectName;

    /**
     * 产品情况
     */
    private String productSituation;

    /**
     * 批次名称
     */
    private String title;

    /**
     * 需求状态
     */
    private String status;

    /**
     * 需求类型
     */
    private String type;

    /**
     * 预估成本(万)
     */
    private Long cost;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 交付时间
     */
    private Date deliveryTime;

    /**
     * 期望交付时间
     */
    private Date expectDeliveryTime;

    /**
     * 签约情况 1是0否
     */
    private Long signingStatus;

    /**
     * 需求确认状态
     */
    private Long confirmStatus;

    /**
     *
     */
    private Long developmentStatus;

    /**
     * 设计状态
     */
    private Long designStatus;

    /**
     *
     */
    private Long testStatus;

    /**
     * 交付状态
     */
    private Long deliveryStatus;

    /**
     * 运维状态
     */
    private Long operationStatus;

    /**
     * 需求附件
     */
    private String attachment;

    /**
     * 审核人
     */
    private Long auditUserId;

    /**
     * 审核已经
     */
    private String auditOpinion;

    /**
     * 审核反馈附件
     */
    private String auditAttachment;

    /**
     * 进度
     */
    private Long progress;

    /**
     * 需求描述
     */
    private String remark;


    /**
     *
     */
    @TableLogic
    private Long delFlag;

    private String workflowInstanceId;
}
