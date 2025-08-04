package org.dromara.project.service;

import org.dromara.project.domain.vo.TowerProjectDemandWeeklyReportVo;
import org.dromara.project.domain.bo.TowerProjectDemandWeeklyReportBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 项目周报Service接口
 *
 * @author Lion Li
 * @date 2025-07-24
 */
public interface ITowerProjectDemandWeeklyReportService {

    /**
     * 查询项目周报
     *
     * @param id 主键
     * @return 项目周报
     */
    TowerProjectDemandWeeklyReportVo queryById(Long id);

    /**
     * 分页查询项目周报列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 项目周报分页列表
     */
    TableDataInfo<TowerProjectDemandWeeklyReportVo> queryPageList(TowerProjectDemandWeeklyReportBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的项目周报列表
     *
     * @param bo 查询条件
     * @return 项目周报列表
     */
    List<TowerProjectDemandWeeklyReportVo> queryList(TowerProjectDemandWeeklyReportBo bo);

    /**
     * 新增项目周报
     *
     * @param bo 项目周报
     * @return 是否新增成功
     */
    Boolean insertByBo(TowerProjectDemandWeeklyReportBo bo);

    /**
     * 修改项目周报
     *
     * @param bo 项目周报
     * @return 是否修改成功
     */
    Boolean updateByBo(TowerProjectDemandWeeklyReportBo bo);

    /**
     * 校验并批量删除项目周报信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
