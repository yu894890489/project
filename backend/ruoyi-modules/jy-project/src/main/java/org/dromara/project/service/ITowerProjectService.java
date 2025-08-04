package org.dromara.project.service;

import org.dromara.project.domain.vo.TowerProjectVo;
import org.dromara.project.domain.bo.TowerProjectBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 项目Service接口
 *
 * @author Lion Li
 * @date 2025-07-24
 */
public interface ITowerProjectService {

    /**
     * 查询项目
     *
     * @param id 主键
     * @return 项目
     */
    TowerProjectVo queryById(Long id);

    /**
     * 分页查询项目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 项目分页列表
     */
    TableDataInfo<TowerProjectVo> queryPageList(TowerProjectBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的项目列表
     *
     * @param bo 查询条件
     * @return 项目列表
     */
    List<TowerProjectVo> queryList(TowerProjectBo bo);

    /**
     * 新增项目
     *
     * @param bo 项目
     * @return 是否新增成功
     */
    Boolean insertByBo(TowerProjectBo bo);

    /**
     * 修改项目
     *
     * @param bo 项目
     * @return 是否修改成功
     */
    Boolean updateByBo(TowerProjectBo bo);

    /**
     * 校验并批量删除项目信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
