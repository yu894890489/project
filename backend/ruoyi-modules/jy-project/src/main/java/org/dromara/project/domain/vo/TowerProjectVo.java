package org.dromara.project.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.project.domain.TowerProject;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 项目视图对象 tower_project
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TowerProject.class)
public class TowerProjectVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String name;

    /**
     * 项目编码
     */
    @ExcelProperty(value = "项目编码")
    private String code;

    /**
     * 项目类型
     */
    @ExcelProperty(value = "项目类型")
    private String type;

    /**
     * 立项时间
     */
    @ExcelProperty(value = "立项时间")
    private Date initiationDate;

    /**
     * 归属区域
     */
    @ExcelProperty(value = "归属区域")
    private Long deptId;

    /**
     * 交付时间
     */
    @ExcelProperty(value = "交付时间")
    private Date deliveryDate;

    /**
     * 项目状态
     */
    @ExcelProperty(value = "项目状态")
    private String status;

    /**
     * 项目金额(万)
     */
    @ExcelProperty(value = "项目金额(万)")
    private Long amount;

    /**
     * 项目进店
     */
    @ExcelProperty(value = "项目进店")
    private Long progress;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String remark;


}
