package org.dromara.project.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 项目对象 tower_project
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tower_project")
public class TowerProject extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
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
    private Long deptId;

    /**
     * 交付时间
     */
    private Date deliveryDate;

    /**
     * 项目状态
     */
    private String status;

    /**
     * 项目金额(万)
     */
    private Long amount;

    /**
     * 项目进店
     */
    private Long progress;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    @TableLogic
    private String delFlag;


}
