package org.dromara.project.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.dromara.project.domain.bo.TowerProjectBo;
import org.dromara.project.domain.vo.TowerProjectVo;
import org.dromara.project.domain.TowerProject;
import org.dromara.project.mapper.TowerProjectMapper;
import org.dromara.project.service.ITowerProjectService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 项目Service业务层处理
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TowerProjectServiceImpl implements ITowerProjectService {

    private final TowerProjectMapper baseMapper;

    /**
     * 查询项目
     *
     * @param id 主键
     * @return 项目
     */
    @Override
    public TowerProjectVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询项目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 项目分页列表
     */
    @Override
    public TableDataInfo<TowerProjectVo> queryPageList(TowerProjectBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TowerProject> lqw = buildQueryWrapper(bo);
        Page<TowerProjectVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的项目列表
     *
     * @param bo 查询条件
     * @return 项目列表
     */
    @Override
    public List<TowerProjectVo> queryList(TowerProjectBo bo) {
        LambdaQueryWrapper<TowerProject> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TowerProject> buildQueryWrapper(TowerProjectBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TowerProject> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(TowerProject::getId);
        lqw.like(StringUtils.isNotBlank(bo.getName()), TowerProject::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), TowerProject::getCode, bo.getCode());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), TowerProject::getType, bo.getType());
        lqw.eq(bo.getInitiationDate() != null, TowerProject::getInitiationDate, bo.getInitiationDate());
        lqw.eq(bo.getDeptId() != null, TowerProject::getDeptId, bo.getDeptId());
        lqw.eq(bo.getDeliveryDate() != null, TowerProject::getDeliveryDate, bo.getDeliveryDate());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TowerProject::getStatus, bo.getStatus());
        lqw.eq(bo.getAmount() != null, TowerProject::getAmount, bo.getAmount());
        lqw.eq(bo.getProgress() != null, TowerProject::getProgress, bo.getProgress());
        return lqw;
    }

    /**
     * 新增项目
     *
     * @param bo 项目
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(TowerProjectBo bo) {
        TowerProject add = MapstructUtils.convert(bo, TowerProject.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改项目
     *
     * @param bo 项目
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(TowerProjectBo bo) {
        TowerProject update = MapstructUtils.convert(bo, TowerProject.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TowerProject entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除项目信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
