<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="项目名称" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入项目名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="项目编码" prop="code">
              <el-input v-model="queryParams.code" placeholder="请输入项目编码" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="立项时间" prop="initiationDate">
              <el-date-picker clearable
                v-model="queryParams.initiationDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择立项时间"
              />
            </el-form-item>
            <el-form-item label="归属区域" prop="deptId">
              <el-input v-model="queryParams.deptId" placeholder="请输入归属区域" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="交付时间" prop="deliveryDate">
              <el-date-picker clearable
                v-model="queryParams.deliveryDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择交付时间"
              />
            </el-form-item>
            <el-form-item label="项目金额(万)" prop="amount">
              <el-input v-model="queryParams.amount" placeholder="请输入项目金额(万)" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="项目进店" prop="progress">
              <el-input v-model="queryParams.progress" placeholder="请输入项目进店" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:project:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['system:project:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['system:project:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:project:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" border :data="projectList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="" align="center" prop="id" v-if="true" />
        <el-table-column label="项目名称" align="center" prop="name" />
        <el-table-column label="项目编码" align="center" prop="code" />
        <el-table-column label="项目类型" align="center" prop="type" />
        <el-table-column label="立项时间" align="center" prop="initiationDate" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.initiationDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="归属区域" align="center" prop="deptId" />
        <el-table-column label="交付时间" align="center" prop="deliveryDate" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="项目状态" align="center" prop="status" />
        <el-table-column label="项目金额(万)" align="center" prop="amount" />
        <el-table-column label="项目进店" align="center" prop="progress" />
        <el-table-column label="" align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:project:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:project:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改项目对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="projectFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="name">
            <el-input v-model="form.name" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="项目编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入项目编码" />
        </el-form-item>
        <el-form-item label="立项时间" prop="initiationDate">
          <el-date-picker clearable
            v-model="form.initiationDate"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择立项时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="归属区域" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入归属区域" />
        </el-form-item>
        <el-form-item label="交付时间" prop="deliveryDate">
          <el-date-picker clearable
            v-model="form.deliveryDate"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择交付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目金额(万)" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入项目金额(万)" />
        </el-form-item>
        <el-form-item label="项目进店" prop="progress">
          <el-input v-model="form.progress" placeholder="请输入项目进店" />
        </el-form-item>
        <el-form-item label="" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="Project" lang="ts">
import { listProject, getProject, delProject, addProject, updateProject } from '@/api/system/project';
import { ProjectVO, ProjectQuery, ProjectForm } from '@/api/system/project/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const projectList = ref<ProjectVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const projectFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: ProjectForm = {
  id: undefined,
  name: undefined,
  code: undefined,
  type: undefined,
  initiationDate: undefined,
  deptId: undefined,
  deliveryDate: undefined,
  status: undefined,
  amount: undefined,
  progress: undefined,
  remark: undefined,
}
const data = reactive<PageData<ProjectForm, ProjectQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    code: undefined,
    type: undefined,
    initiationDate: undefined,
    deptId: undefined,
    deliveryDate: undefined,
    status: undefined,
    amount: undefined,
    progress: undefined,
    params: {
    }
  },
  rules: {
    deptId: [
      { required: true, message: "归属区域不能为空", trigger: "blur" }
    ],
    deliveryDate: [
      { required: true, message: "交付时间不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "项目状态不能为空", trigger: "change" }
    ],
    amount: [
      { required: true, message: "项目金额(万)不能为空", trigger: "blur" }
    ],
    remark: [
      { required: true, message: "不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询项目列表 */
const getList = async () => {
  loading.value = true;
  const res = await listProject(queryParams.value);
  projectList.value = res.rows;
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
  projectFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: ProjectVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加项目";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: ProjectVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getProject(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改项目";
}

/** 提交按钮 */
const submitForm = () => {
  projectFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateProject(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addProject(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: ProjectVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除项目编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delProject(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('system/project/export', {
    ...queryParams.value
  }, `project_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
