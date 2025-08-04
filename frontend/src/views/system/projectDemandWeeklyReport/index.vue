<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="标题" prop="title">
              <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="需求id" prop="projectDemandId">
              <el-input v-model="queryParams.projectDemandId" placeholder="请输入需求id" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="填报日期" prop="fllingDate">
              <el-date-picker clearable
                v-model="queryParams.fllingDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择填报日期"
              />
            </el-form-item>
            <el-form-item label="附件" prop="attachment">
              <el-input v-model="queryParams.attachment" placeholder="请输入附件" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
              <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </transition>

    <el-card shadow="never">
      <template #header>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:projectDemandWeeklyReport:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['system:projectDemandWeeklyReport:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['system:projectDemandWeeklyReport:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:projectDemandWeeklyReport:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" border :data="projectDemandWeeklyReportList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="" align="center" prop="id" v-if="true" />
        <el-table-column label="标题" align="center" prop="title" />
        <el-table-column label="需求id" align="center" prop="projectDemandId" />
        <el-table-column label="填报日期" align="center" prop="fllingDate" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.fllingDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="内容" align="center" prop="content" />
        <el-table-column label="附件" align="center" prop="attachment" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:projectDemandWeeklyReport:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:projectDemandWeeklyReport:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改项目周报对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="projectDemandWeeklyReportFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
            <el-input v-model="form.title" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="需求id" prop="projectDemandId">
          <el-input v-model="form.projectDemandId" placeholder="请输入需求id" />
        </el-form-item>
        <el-form-item label="填报日期" prop="fllingDate">
          <el-date-picker clearable
            v-model="form.fllingDate"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择填报日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="附件" prop="attachment">
            <el-input v-model="form.attachment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ProjectDemandWeeklyReport" lang="ts">
import { listProjectDemandWeeklyReport, getProjectDemandWeeklyReport, delProjectDemandWeeklyReport, addProjectDemandWeeklyReport, updateProjectDemandWeeklyReport } from '@/api/system/projectDemandWeeklyReport';
import { ProjectDemandWeeklyReportVO, ProjectDemandWeeklyReportQuery, ProjectDemandWeeklyReportForm } from '@/api/system/projectDemandWeeklyReport/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const projectDemandWeeklyReportList = ref<ProjectDemandWeeklyReportVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const projectDemandWeeklyReportFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: ProjectDemandWeeklyReportForm = {
  id: undefined,
  title: undefined,
  projectDemandId: undefined,
  fllingDate: undefined,
  content: undefined,
  attachment: undefined,
}
const data = reactive<PageData<ProjectDemandWeeklyReportForm, ProjectDemandWeeklyReportQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    title: undefined,
    projectDemandId: undefined,
    fllingDate: undefined,
    content: undefined,
    attachment: undefined,
    params: {
    }
  },
  rules: {
    title: [
      { required: true, message: "标题不能为空", trigger: "blur" }
    ],
    projectDemandId: [
      { required: true, message: "需求id不能为空", trigger: "blur" }
    ],
    fllingDate: [
      { required: true, message: "填报日期不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "内容不能为空", trigger: "blur" }
    ],
    attachment: [
      { required: true, message: "附件不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询项目周报列表 */
const getList = async () => {
  loading.value = true;
  const res = await listProjectDemandWeeklyReport(queryParams.value);
  projectDemandWeeklyReportList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 取消按钮 */
const cancel = () => {
  reset();
  dialog.visible = false;
}

/** 表单重置 */
const reset = () => {
  form.value = {...initFormData};
  projectDemandWeeklyReportFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 多选框选中数据 */
const handleSelectionChange = (selection: ProjectDemandWeeklyReportVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加项目周报";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: ProjectDemandWeeklyReportVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getProjectDemandWeeklyReport(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改项目周报";
}

/** 提交按钮 */
const submitForm = () => {
  projectDemandWeeklyReportFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateProjectDemandWeeklyReport(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addProjectDemandWeeklyReport(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: ProjectDemandWeeklyReportVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除项目周报编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delProjectDemandWeeklyReport(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('system/projectDemandWeeklyReport/export', {
    ...queryParams.value
  }, `projectDemandWeeklyReport_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
