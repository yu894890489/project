package org.dromara.project.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.project.domain.TowerProjectDemandWeeklyReport;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 项目周报视图对象 tower_project_demand_weekly_report
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TowerProjectDemandWeeklyReport.class)
public class TowerProjectDemandWeeklyReportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 需求id
     */
    @ExcelProperty(value = "需求id")
    private Long projectDemandId;

    /**
     * 填报日期
     */
    @ExcelProperty(value = "填报日期")
    private Date fllingDate;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 附件
     */
    @ExcelProperty(value = "附件")
    private String attachment;


}
