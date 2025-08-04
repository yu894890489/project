package org.dromara.project.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.project.domain.TowerProjectDemand;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 需求管理视图对象 tower_project_demand
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TowerProjectDemand.class)
public class TowerProjectDemandVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long projectId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long deptId;

    /**
     * 需求名称
     */
    @ExcelProperty(value = "需求名称")
    private String name;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String defineProjectName;

    /**
     * 产品情况
     */
    @ExcelProperty(value = "产品情况")
    private String productSituation;

    /**
     * 批次名称
     */
    @ExcelProperty(value = "批次名称")
    private String title;

    /**
     * 需求状态
     */
    @ExcelProperty(value = "需求状态")
    private String status;

    /**
     * 需求类型
     */
    @ExcelProperty(value = "需求类型")
    private String type;

    /**
     * 预估成本(万)
     */
    @ExcelProperty(value = "预估成本(万)")
    private Long cost;

    /**
     * 开始时间
     */
    @ExcelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 交付时间
     */
    @ExcelProperty(value = "交付时间")
    private Date deliveryTime;

    /**
     * 期望交付时间
     */
    @ExcelProperty(value = "期望交付时间")
    private Date expectDeliveryTime;

    /**
     * 签约情况 1是0否
     */
    @ExcelProperty(value = "签约情况 1是0否")
    private Long signingStatus;

    /**
     * 需求确认状态
     */
    @ExcelProperty(value = "需求确认状态")
    private Long confirmStatus;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long developmentStatus;

    /**
     * 设计状态
     */
    @ExcelProperty(value = "设计状态")
    private Long designStatus;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long testStatus;

    /**
     * 交付状态
     */
    @ExcelProperty(value = "交付状态")
    private Long deliveryStatus;

    /**
     * 运维状态
     */
    @ExcelProperty(value = "运维状态")
    private Long operationStatus;

    /**
     * 需求附件
     */
    @ExcelProperty(value = "需求附件 ")
    private String attachment;

    /**
     * 审核人
     */
    @ExcelProperty(value = "审核人")
    private Long auditUserId;

    /**
     * 审核已经
     */
    @ExcelProperty(value = "审核已经")
    private String auditOpinion;

    /**
     * 审核反馈附件
     */
    @ExcelProperty(value = "审核反馈附件")
    private String auditAttachment;

    /**
     * 进度
     */
    @ExcelProperty(value = "进度")
    private Long progress;

    /**
     * 需求描述
     */
    @ExcelProperty(value = "需求描述")
    private String remark;



}
