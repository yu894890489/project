package org.dromara.project.domain.bo;

import org.dromara.project.domain.TowerProjectDemand;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 需求管理业务对象 tower_project_demand
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TowerProjectDemand.class, reverseConvertGenerate = false)
public class TowerProjectDemandBo extends BaseEntity {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long projectId;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 需求名称
     */
    @NotBlank(message = "需求名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String defineProjectName;

    /**
     * 产品情况
     */
    @NotBlank(message = "产品情况不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotNull(message = "开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 交付时间
     */
    @NotNull(message = "交付时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date deliveryTime;

    /**
     * 期望交付时间
     */
    @NotNull(message = "期望交付时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date expectDeliveryTime;

    /**
     * 签约情况 1是0否
     */
    @NotNull(message = "签约情况 1是0否不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long signingStatus;

    /**
     * 需求确认状态
     */
    @NotNull(message = "需求确认状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long confirmStatus;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long developmentStatus;

    /**
     * 设计状态
     */
    @NotNull(message = "设计状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long designStatus;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long testStatus;

    /**
     * 交付状态
     */
    @NotNull(message = "交付状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deliveryStatus;

    /**
     * 运维状态
     */
    @NotNull(message = "运维状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long operationStatus;

    /**
     * 需求附件
     */
    @NotBlank(message = "需求附件 不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachment;

    /**
     * 审核人
     */
    @NotNull(message = "审核人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long auditUserId;

    /**
     * 审核已经
     */
    @NotBlank(message = "审核已经不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditOpinion;

    /**
     * 审核反馈附件
     */
    @NotBlank(message = "审核反馈附件不能为空", groups = { AddGroup.class, EditGroup.class })
    private String auditAttachment;

    /**
     * 进度
     */
    @NotNull(message = "进度不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long progress;

    /**
     * 需求描述
     */
    @NotBlank(message = "需求描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;



}
