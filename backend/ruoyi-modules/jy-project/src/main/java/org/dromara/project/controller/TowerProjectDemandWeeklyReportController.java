package org.dromara.project.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.project.domain.vo.TowerProjectDemandWeeklyReportVo;
import org.dromara.project.domain.bo.TowerProjectDemandWeeklyReportBo;
import org.dromara.project.service.ITowerProjectDemandWeeklyReportService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 项目周报
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/projectDemandWeeklyReport")
public class TowerProjectDemandWeeklyReportController extends BaseController {

    private final ITowerProjectDemandWeeklyReportService towerProjectDemandWeeklyReportService;

    /**
     * 查询项目周报列表
     */
    @SaCheckPermission("system:projectDemandWeeklyReport:list")
    @GetMapping("/list")
    public TableDataInfo<TowerProjectDemandWeeklyReportVo> list(TowerProjectDemandWeeklyReportBo bo, PageQuery pageQuery) {
        return towerProjectDemandWeeklyReportService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出项目周报列表
     */
    @SaCheckPermission("system:projectDemandWeeklyReport:export")
    @Log(title = "项目周报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TowerProjectDemandWeeklyReportBo bo, HttpServletResponse response) {
        List<TowerProjectDemandWeeklyReportVo> list = towerProjectDemandWeeklyReportService.queryList(bo);
        ExcelUtil.exportExcel(list, "项目周报", TowerProjectDemandWeeklyReportVo.class, response);
    }

    /**
     * 获取项目周报详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:projectDemandWeeklyReport:query")
    @GetMapping("/{id}")
    public R<TowerProjectDemandWeeklyReportVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(towerProjectDemandWeeklyReportService.queryById(id));
    }

    /**
     * 新增项目周报
     */
    @SaCheckPermission("system:projectDemandWeeklyReport:add")
    @Log(title = "项目周报", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TowerProjectDemandWeeklyReportBo bo) {
        return toAjax(towerProjectDemandWeeklyReportService.insertByBo(bo));
    }

    /**
     * 修改项目周报
     */
    @SaCheckPermission("system:projectDemandWeeklyReport:edit")
    @Log(title = "项目周报", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TowerProjectDemandWeeklyReportBo bo) {
        return toAjax(towerProjectDemandWeeklyReportService.updateByBo(bo));
    }

    /**
     * 删除项目周报
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:projectDemandWeeklyReport:remove")
    @Log(title = "项目周报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(towerProjectDemandWeeklyReportService.deleteWithValidByIds(List.of(ids), true));
    }
}
