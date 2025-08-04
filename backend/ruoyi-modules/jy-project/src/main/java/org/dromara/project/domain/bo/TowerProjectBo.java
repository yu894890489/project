package org.dromara.project.domain.bo;

import org.dromara.project.domain.TowerProject;
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
 * 项目业务对象 tower_project
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TowerProject.class, reverseConvertGenerate = false)
public class TowerProjectBo extends BaseEntity {

    /**
     *
     */
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目编码
     */
    private String code;

    /**
     * 项目类型
     */
    private String type;

    /**
     * 立项时间
     */
    private Date initiationDate;

    /**
     * 归属区域
     */
    @NotNull(message = "归属区域不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 交付时间
     */
    @NotNull(message = "交付时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date deliveryDate;

    /**
     * 项目状态
     */
    @NotBlank(message = "项目状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 项目金额(万)
     */
    @NotNull(message = "项目金额(万)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 项目进店
     */
    private Long progress;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
