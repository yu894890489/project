package org.dromara.project.service;

import org.dromara.project.domain.vo.TowerProjectDemandVo;
import org.dromara.project.domain.bo.TowerProjectDemandBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 需求管理Service接口
 *
 * @author Lion Li
 * @date 2025-07-24
 */
public interface ITowerProjectDemandService {

    /**
     * 查询需求管理
     *
     * @param id 主键
     * @return 需求管理
     */
    TowerProjectDemandVo queryById(Long id);

    /**
     * 分页查询需求管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 需求管理分页列表
     */
    TableDataInfo<TowerProjectDemandVo> queryPageList(TowerProjectDemandBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的需求管理列表
     *
     * @param bo 查询条件
     * @return 需求管理列表
     */
    List<TowerProjectDemandVo> queryList(TowerProjectDemandBo bo);

    /**
     * 新增需求管理
     *
     * @param bo 需求管理
     * @return 是否新增成功
     */
    Boolean insertByBo(TowerProjectDemandBo bo);

    /**
     * 修改需求管理
     *
     * @param bo 需求管理
     * @return 是否修改成功
     */
    Boolean updateByBo(TowerProjectDemandBo bo);

    /**
     * 校验并批量删除需求管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
