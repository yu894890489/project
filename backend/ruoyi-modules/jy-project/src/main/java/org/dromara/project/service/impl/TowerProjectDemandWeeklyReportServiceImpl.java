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
import org.dromara.project.domain.bo.TowerProjectDemandWeeklyReportBo;
import org.dromara.project.domain.vo.TowerProjectDemandWeeklyReportVo;
import org.dromara.project.domain.TowerProjectDemandWeeklyReport;
import org.dromara.project.mapper.TowerProjectDemandWeeklyReportMapper;
import org.dromara.project.service.ITowerProjectDemandWeeklyReportService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 项目周报Service业务层处理
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TowerProjectDemandWeeklyReportServiceImpl implements ITowerProjectDemandWeeklyReportService {

    private final TowerProjectDemandWeeklyReportMapper baseMapper;

    /**
     * 查询项目周报
     *
     * @param id 主键
     * @return 项目周报
     */
    @Override
    public TowerProjectDemandWeeklyReportVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询项目周报列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 项目周报分页列表
     */
    @Override
    public TableDataInfo<TowerProjectDemandWeeklyReportVo> queryPageList(TowerProjectDemandWeeklyReportBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<TowerProjectDemandWeeklyReport> lqw = buildQueryWrapper(bo);
        Page<TowerProjectDemandWeeklyReportVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的项目周报列表
     *
     * @param bo 查询条件
     * @return 项目周报列表
     */
    @Override
    public List<TowerProjectDemandWeeklyReportVo> queryList(TowerProjectDemandWeeklyReportBo bo) {
        LambdaQueryWrapper<TowerProjectDemandWeeklyReport> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<TowerProjectDemandWeeklyReport> buildQueryWrapper(TowerProjectDemandWeeklyReportBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<TowerProjectDemandWeeklyReport> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(TowerProjectDemandWeeklyReport::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), TowerProjectDemandWeeklyReport::getTitle, bo.getTitle());
        lqw.eq(bo.getProjectDemandId() != null, TowerProjectDemandWeeklyReport::getProjectDemandId, bo.getProjectDemandId());
        lqw.eq(bo.getFllingDate() != null, TowerProjectDemandWeeklyReport::getFllingDate, bo.getFllingDate());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), TowerProjectDemandWeeklyReport::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getAttachment()), TowerProjectDemandWeeklyReport::getAttachment, bo.getAttachment());
        return lqw;
    }

    /**
     * 新增项目周报
     *
     * @param bo 项目周报
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(TowerProjectDemandWeeklyReportBo bo) {
        TowerProjectDemandWeeklyReport add = MapstructUtils.convert(bo, TowerProjectDemandWeeklyReport.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改项目周报
     *
     * @param bo 项目周报
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(TowerProjectDemandWeeklyReportBo bo) {
        TowerProjectDemandWeeklyReport update = MapstructUtils.convert(bo, TowerProjectDemandWeeklyReport.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(TowerProjectDemandWeeklyReport entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除项目周报信息
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
