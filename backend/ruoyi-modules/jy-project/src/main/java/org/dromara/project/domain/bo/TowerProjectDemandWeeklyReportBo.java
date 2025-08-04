package org.dromara.project.domain.bo;

import org.dromara.project.domain.TowerProjectDemandWeeklyReport;
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
 * 项目周报业务对象 tower_project_demand_weekly_report
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TowerProjectDemandWeeklyReport.class, reverseConvertGenerate = false)
public class TowerProjectDemandWeeklyReportBo extends BaseEntity {

    /**
     *
     */
    private Long id;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 需求id
     */
    @NotNull(message = "需求id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long projectDemandId;

    /**
     * 填报日期
     */
    @NotNull(message = "填报日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date fllingDate;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 附件
     */
    @NotBlank(message = "附件不能为空", groups = { AddGroup.class, EditGroup.class })
    private String attachment;


}
