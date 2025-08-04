<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="需求名称" prop="name">
              <el-input v-model="queryParams.name" placeholder="请输入需求名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="项目名称" prop="defineProjectName">
              <el-input v-model="queryParams.defineProjectName" placeholder="请输入项目名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="审核人" prop="auditUserId">
              <el-input v-model="queryParams.auditUserId" placeholder="请输入审核人" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="审核已经" prop="auditOpinion">
              <el-input v-model="queryParams.auditOpinion" placeholder="请输入审核已经" clearable @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['system:projectDemand:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['system:projectDemand:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['system:projectDemand:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['system:projectDemand:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" border :data="projectDemandList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="需求名称" align="center" prop="name" />
        <el-table-column label="项目名称" align="center" prop="defineProjectName" />
        <el-table-column label="产品情况" align="center" prop="productSituation" />
        <el-table-column label="批次名称" align="center" prop="title" />
        <el-table-column label="需求状态" align="center" prop="status" />
        <el-table-column label="需求类型" align="center" prop="type" />
        <el-table-column label="预估成本(万)" align="center" prop="cost" />
        <el-table-column label="开始时间" align="center" prop="startTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="交付时间" align="center" prop="deliveryTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.deliveryTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="期望交付时间" align="center" prop="expectDeliveryTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.expectDeliveryTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="签约情况 1是0否" align="center" prop="signingStatus" />
        <el-table-column label="需求确认状态" align="center" prop="confirmStatus" />
        <el-table-column label="" align="center" prop="developmentStatus" />
        <el-table-column label="设计状态" align="center" prop="designStatus" />
        <el-table-column label="" align="center" prop="testStatus" />
        <!-- <el-table-column label="交付状态" align="center" prop="deliveryStatus" /> -->
        <el-table-column label="运维状态" align="center" prop="operationStatus" />
        <el-table-column label="需求附件 " align="center" prop="attachment" />
        <el-table-column label="审核人" align="center" prop="auditUserId" />
        <el-table-column label="审核已经" align="center" prop="auditOpinion" />
        <el-table-column label="审核反馈附件" align="center" prop="auditAttachment" />
        <el-table-column label="进度" align="center" prop="progress" />
        <el-table-column label="流程状态" align="center" prop="workflowStatus" width="120">
          <template #default="scope">
            <el-tag v-if="scope.row.workflowInstanceId" type="success" size="small">已启动</el-tag>
            <el-tag v-else type="info" size="small">未启动</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="需求描述" align="center" prop="remark" show-overflow-tooltip />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:projectDemand:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:projectDemand:remove']"></el-button>
            </el-tooltip>
            <el-tooltip content="启动流程" placement="top" v-if="!scope.row.workflowInstanceId">
              <el-button link type="success" icon="VideoPlay" @click="handleStartWorkflow(scope.row)" 
                v-hasPermi="['workflow:instance:start']">启动</el-button>
            </el-tooltip>
            <el-tooltip content="查看流程" placement="top" v-if="scope.row.workflowInstanceId">
              <el-button link type="info" icon="View" @click="handleViewWorkflow(scope.row)" 
                v-hasPermi="['workflow:instance:query']">流程</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改需求管理对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="800px" append-to-body>
      <el-form ref="projectDemandFormRef" :model="form" :rules="rules" label-width="120px" :inline="true">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="需求名称" prop="name">
                <el-input v-model="form.name" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="defineProjectName">
              <el-input v-model="form.defineProjectName" placeholder="请输入项目名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品情况" prop="productSituation">
              <el-input v-model="form.productSituation" placeholder="请输入产品情况" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="批次名称" prop="title">
              <el-input v-model="form.title" placeholder="请输入批次名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预估成本(万)" prop="cost">
              <el-input v-model="form.cost" placeholder="请输入预估成本(万)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker clearable
                v-model="form.startTime"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择开始时间"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="交付时间" prop="deliveryTime">
              <el-date-picker clearable
                v-model="form.deliveryTime"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择交付时间"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="期望交付时间" prop="expectDeliveryTime">
              <el-date-picker clearable
                v-model="form.expectDeliveryTime"
                type="datetime"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择期望交付时间"
                style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需求附件 " prop="attachment">
                <el-input v-model="form.attachment" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核人" prop="auditUserId">
              <el-input v-model="form.auditUserId" placeholder="请输入审核人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核已经" prop="auditOpinion">
                <el-input v-model="form.auditOpinion" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审核反馈附件" prop="auditAttachment">
                <el-input v-model="form.auditAttachment" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进度" prop="progress">
              <el-input v-model="form.progress" placeholder="请输入进度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="需求描述" prop="remark">
                <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
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

<script setup name="ProjectDemand" lang="ts">
import { listProjectDemand, getProjectDemand, delProjectDemand, addProjectDemand, updateProjectDemand } from '@/api/system/projectDemand';
import { ProjectDemandVO, ProjectDemandQuery, ProjectDemandForm } from '@/api/system/projectDemand/types';
import { startWorkflow } from '@/api/workflow';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const projectDemandList = ref<ProjectDemandVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const projectDemandFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: ProjectDemandForm = {
  id: undefined,
  projectId: undefined,
  deptId: undefined,
  name: undefined,
  defineProjectName: undefined,
  productSituation: undefined,
  title: undefined,
  status: undefined,
  type: undefined,
  cost: undefined,
  startTime: undefined,
  deliveryTime: undefined,
  expectDeliveryTime: undefined,
  signingStatus: undefined,
  confirmStatus: undefined,
  developmentStatus: undefined,
  designStatus: undefined,
  testStatus: undefined,
  deliveryStatus: undefined,
  operationStatus: undefined,
  attachment: undefined,
  auditUserId: undefined,
  auditOpinion: undefined,
  auditAttachment: undefined,
  progress: undefined,
  remark: undefined,
  workflowInstanceId: undefined,
  workflowStatus: 0,
}
const data = reactive<PageData<ProjectDemandForm, ProjectDemandQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    projectId: undefined,
    deptId: undefined,
    name: undefined,
    defineProjectName: undefined,
    productSituation: undefined,
    title: undefined,
    status: undefined,
    type: undefined,
    cost: undefined,
    startTime: undefined,
    deliveryTime: undefined,
    expectDeliveryTime: undefined,
    signingStatus: undefined,
    confirmStatus: undefined,
    developmentStatus: undefined,
    designStatus: undefined,
    testStatus: undefined,
    deliveryStatus: undefined,
    operationStatus: undefined,
    attachment: undefined,
    auditUserId: undefined,
    auditOpinion: undefined,
    auditAttachment: undefined,
    progress: undefined,
    params: {
    }
  },
  rules: {
    name: [
      { required: true, message: "需求名称不能为空", trigger: "blur" }
    ],
    defineProjectName: [
      { required: true, message: "项目名称不能为空", trigger: "blur" }
    ],
    productSituation: [
      { required: true, message: "产品情况不能为空", trigger: "blur" }
    ],
    startTime: [
      { required: true, message: "开始时间不能为空", trigger: "blur" }
    ],
    deliveryTime: [
      { required: true, message: "交付时间不能为空", trigger: "blur" }
    ],
    expectDeliveryTime: [
      { required: true, message: "期望交付时间不能为空", trigger: "blur" }
    ],
    signingStatus: [
      { required: true, message: "签约情况 1是0否不能为空", trigger: "change" }
    ],
    confirmStatus: [
      { required: true, message: "需求确认状态不能为空", trigger: "change" }
    ],
    developmentStatus: [
      { required: true, message: "不能为空", trigger: "change" }
    ],
    designStatus: [
      { required: true, message: "设计状态不能为空", trigger: "change" }
    ],
    testStatus: [
      { required: true, message: "不能为空", trigger: "change" }
    ],
    operationStatus: [
      { required: true, message: "运维状态不能为空", trigger: "change" }
    ],
    attachment: [
      { required: true, message: "需求附件 不能为空", trigger: "blur" }
    ],
    auditUserId: [
      { required: true, message: "审核人不能为空", trigger: "blur" }
    ],
    auditOpinion: [
      { required: true, message: "审核已经不能为空", trigger: "blur" }
    ],
    auditAttachment: [
      { required: true, message: "审核反馈附件不能为空", trigger: "blur" }
    ],
    progress: [
      { required: true, message: "进度不能为空", trigger: "blur" }
    ],
    remark: [
      { required: true, message: "需求描述不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询需求管理列表 */
const getList = async () => {
  loading.value = true;
  const res = await listProjectDemand(queryParams.value);
  projectDemandList.value = res.rows;
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
  projectDemandFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: ProjectDemandVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加需求管理";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: ProjectDemandVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getProjectDemand(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改需求管理";
}

/** 提交按钮 */
const submitForm = () => {
  projectDemandFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateProjectDemand(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addProjectDemand(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: ProjectDemandVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除需求管理编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delProjectDemand(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('system/projectDemand/export', {
    ...queryParams.value
  }, `projectDemand_${new Date().getTime()}.xlsx`)
}

/** 启动工作流 */
const handleStartWorkflow = async (row?: ProjectDemandVO) => {
  try {
    await proxy?.$modal.confirm('确认启动该需求的审核流程吗？');
    
    const params = {
      flowCode: 'da',
      variables: {
        projectDemandId: row?.id,
        projectName: row?.defineProjectName,
        demandName: row?.name,
        createUser: proxy?.$auth.user?.nickName || '系统用户',
        createTime: new Date().toISOString()
      }
    };
    
    // 调用启动工作流API
    const result = await startWorkflow(params);
    
    // 更新需求记录的工作流实例ID和状态
    const updateData = {
      ...row,
      workflowInstanceId: result.data.instanceId,
      workflowStatus: 1,
      status: '审核中' // 更新需求状态为审核中
    };
    await updateProjectDemand(updateData);
    
    proxy?.$modal.msgSuccess("工作流启动成功！流程已进入审核阶段。");
    await getList(); // 刷新列表
  } catch (error) {
    if (error !== 'cancel') {
      console.error('启动工作流失败:', error);
      proxy?.$modal.msgError("工作流启动失败！请检查网络连接或联系管理员。");
    }
  }
}

/** 查看工作流 */
const handleViewWorkflow = async (row?: ProjectDemandVO) => {
  if (!row?.workflowInstanceId) {
    proxy?.$modal.msgError("该需求尚未启动工作流！");
    return;
  }
  
  try {
    // 获取工作流实例信息，判断当前用户是否有处理权限
    // 这里简化处理，直接跳转到处理页面
    proxy?.$router.push({
      path: '/project/workflow/project',
      query: {
        instanceId: row.workflowInstanceId,
        demandId: row.id,
        mode: 'view' // 标识为查看模式
      }
    });
  } catch (error) {
    console.error('查看工作流失败:', error);
    proxy?.$modal.msgError("查看工作流失败！");
  }
}

onMounted(() => {
  getList();
});
</script>
