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
import org.dromara.project.domain.bo.TowerProjectDemandBo;
import org.dromara.project.domain.vo.TowerProjectDemandVo;
import org.dromara.project.domain.TowerProjectDemand;
import org.dromara.project.mapper.TowerProjectDemandMapper;
import org.dromara.project.service.ITowerProjectDemandService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 需求管理Service业务层处理
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TowerProjectDemandServiceImpl implements ITowerProjectDemandService {

    private final TowerProjectDemandMapper baseMapper;

    /**
     * 查询需求管理
     *
     * @param id 主键
     * @return 需求管理
     */
    @Override
    public TowerProjectDemandVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询需求管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 需求管理分页列表
     */
    @Override
    public TableDataInfo<TowerProjectDemandVo> queryPageList(TowerProjectDemandBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TowerProjectDemand> lqw = buildQueryWrapper(bo);
        Page<TowerProjectDemandVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的需求管理列表
     *
     * @param bo 查询条件
     * @return 需求管理列表
     */
    @Override
    public List<TowerProjectDemandVo> queryList(TowerProjectDemandBo bo) {
        LambdaQueryWrapper<TowerProjectDemand> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TowerProjectDemand> buildQueryWrapper(TowerProjectDemandBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TowerProjectDemand> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(TowerProjectDemand::getId);
        lqw.eq(bo.getProjectId() != null, TowerProjectDemand::getProjectId, bo.getProjectId());
        lqw.eq(bo.getDeptId() != null, TowerProjectDemand::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), TowerProjectDemand::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getDefineProjectName()), TowerProjectDemand::getDefineProjectName, bo.getDefineProjectName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductSituation()), TowerProjectDemand::getProductSituation, bo.getProductSituation());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), TowerProjectDemand::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), TowerProjectDemand::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), TowerProjectDemand::getType, bo.getType());
        lqw.eq(bo.getCost() != null, TowerProjectDemand::getCost, bo.getCost());
        lqw.eq(bo.getStartTime() != null, TowerProjectDemand::getStartTime, bo.getStartTime());
        lqw.eq(bo.getDeliveryTime() != null, TowerProjectDemand::getDeliveryTime, bo.getDeliveryTime());
        lqw.eq(bo.getExpectDeliveryTime() != null, TowerProjectDemand::getExpectDeliveryTime, bo.getExpectDeliveryTime());
        lqw.eq(bo.getSigningStatus() != null, TowerProjectDemand::getSigningStatus, bo.getSigningStatus());
        lqw.eq(bo.getConfirmStatus() != null, TowerProjectDemand::getConfirmStatus, bo.getConfirmStatus());
        lqw.eq(bo.getDevelopmentStatus() != null, TowerProjectDemand::getDevelopmentStatus, bo.getDevelopmentStatus());
        lqw.eq(bo.getDesignStatus() != null, TowerProjectDemand::getDesignStatus, bo.getDesignStatus());
        lqw.eq(bo.getTestStatus() != null, TowerProjectDemand::getTestStatus, bo.getTestStatus());
        lqw.eq(bo.getDeliveryStatus() != null, TowerProjectDemand::getDeliveryStatus, bo.getDeliveryStatus());
        lqw.eq(bo.getOperationStatus() != null, TowerProjectDemand::getOperationStatus, bo.getOperationStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getAttachment()), TowerProjectDemand::getAttachment, bo.getAttachment());
        lqw.eq(bo.getAuditUserId() != null, TowerProjectDemand::getAuditUserId, bo.getAuditUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditOpinion()), TowerProjectDemand::getAuditOpinion, bo.getAuditOpinion());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditAttachment()), TowerProjectDemand::getAuditAttachment, bo.getAuditAttachment());
        lqw.eq(bo.getProgress() != null, TowerProjectDemand::getProgress, bo.getProgress());
        return lqw;
    }

    /**
     * 新增需求管理
     *
     * @param bo 需求管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(TowerProjectDemandBo bo) {
        TowerProjectDemand add = MapstructUtils.convert(bo, TowerProjectDemand.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改需求管理
     *
     * @param bo 需求管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(TowerProjectDemandBo bo) {
        TowerProjectDemand update = MapstructUtils.convert(bo, TowerProjectDemand.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TowerProjectDemand entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除需求管理信息
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
