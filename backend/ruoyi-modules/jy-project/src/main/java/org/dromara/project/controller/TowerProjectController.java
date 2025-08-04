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
import org.dromara.project.domain.vo.TowerProjectVo;
import org.dromara.project.domain.bo.TowerProjectBo;
import org.dromara.project.service.ITowerProjectService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 项目
 *
 * @author Lion Li
 * @date 2025-07-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/project")
public class TowerProjectController extends BaseController {

    private final ITowerProjectService towerProjectService;

    /**
     * 查询项目列表
     */
    @SaCheckPermission("system:project:list")
    @GetMapping("/list")
    public TableDataInfo<TowerProjectVo> list(TowerProjectBo bo, PageQuery pageQuery) {
        return towerProjectService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出项目列表
     */
    @SaCheckPermission("system:project:export")
    @Log(title = "项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TowerProjectBo bo, HttpServletResponse response) {
        List<TowerProjectVo> list = towerProjectService.queryList(bo);
        ExcelUtil.exportExcel(list, "项目", TowerProjectVo.class, response);
    }

    /**
     * 获取项目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:project:query")
    @GetMapping("/{id}")
    public R<TowerProjectVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(towerProjectService.queryById(id));
    }

    /**
     * 新增项目
     */
    @SaCheckPermission("system:project:add")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TowerProjectBo bo) {
        return toAjax(towerProjectService.insertByBo(bo));
    }

    /**
     * 修改项目
     */
    @SaCheckPermission("system:project:edit")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TowerProjectBo bo) {
        return toAjax(towerProjectService.updateByBo(bo));
    }

    /**
     * 删除项目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:project:remove")
    @Log(title = "项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(towerProjectService.deleteWithValidByIds(List.of(ids), true));
    }
}
