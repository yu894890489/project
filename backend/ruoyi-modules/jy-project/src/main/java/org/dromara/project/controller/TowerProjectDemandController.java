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
import org.dromara.project.domain.vo.TowerProjectDemandVo;
import org.dromara.project.domain.bo.TowerProjectDemandBo;
import org.dromara.project.service.ITowerProjectDemandService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 需求管理
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/projectDemand")
public class TowerProjectDemandController extends BaseController {

    private final ITowerProjectDemandService towerProjectDemandService;

    /**
     * 查询需求管理列表
     */
    @SaCheckPermission("system:projectDemand:list")
    @GetMapping("/list")
    public TableDataInfo<TowerProjectDemandVo> list(TowerProjectDemandBo bo, PageQuery pageQuery) {
        return towerProjectDemandService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出需求管理列表
     */
    @SaCheckPermission("system:projectDemand:export")
    @Log(title = "需求管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TowerProjectDemandBo bo, HttpServletResponse response) {
        List<TowerProjectDemandVo> list = towerProjectDemandService.queryList(bo);
        ExcelUtil.exportExcel(list, "需求管理", TowerProjectDemandVo.class, response);
    }

    /**
     * 获取需求管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:projectDemand:query")
    @GetMapping("/{id}")
    public R<TowerProjectDemandVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(towerProjectDemandService.queryById(id));
    }

    /**
     * 新增需求管理
     */
    @SaCheckPermission("system:projectDemand:add")
    @Log(title = "需求管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TowerProjectDemandBo bo) {
        return toAjax(towerProjectDemandService.insertByBo(bo));
    }

    /**
     * 修改需求管理
     */
    @SaCheckPermission("system:projectDemand:edit")
    @Log(title = "需求管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TowerProjectDemandBo bo) {
        return toAjax(towerProjectDemandService.updateByBo(bo));
    }

    /**
     * 删除需求管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:projectDemand:remove")
    @Log(title = "需求管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(towerProjectDemandService.deleteWithValidByIds(List.of(ids), true));
    }
}
