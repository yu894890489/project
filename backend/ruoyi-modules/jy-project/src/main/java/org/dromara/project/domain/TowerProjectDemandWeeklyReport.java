package org.dromara.project.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 项目周报对象 tower_project_demand_weekly_report
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tower_project_demand_weekly_report")
public class TowerProjectDemandWeeklyReport extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 需求id
     */
    private Long projectDemandId;

    /**
     * 填报日期
     */
    private Date fllingDate;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件
     */
    private String attachment;

    /**
     *
     */
    @TableLogic
    private Long delFlag;


}
