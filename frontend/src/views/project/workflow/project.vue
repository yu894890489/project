<template>
  <div class="project-workflow-container">
    <!-- 项目基本信息 -->
    <el-card class="mb-4">
      <template #header>
        <span class="card-title">项目基本信息</span>
      </template>

      <el-form ref="projectFormRef" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入项目名称" maxlength="30" :disabled="!canEdit" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目类型" prop="type">
              <el-select v-model="form.type" placeholder="项目类型" clearable style="width: 100%" :disabled="!canEdit">
                <el-option v-for="dict in manage_project_type" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="归属区域" prop="deptId">
              <el-tree-select class="mt-2" node-key="id" :data="enabledDeptOptions"
                :props="{ label: 'label', value: 'id' } as any" 
                 :disabled="!canEdit" 
                 :render-after-expand="false"
                v-model="form.deptId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="立项时间" prop="initiationDate" v-if="workflowInstanceId === ''">
              <el-date-picker v-model="form.initiationDate" value-format="yyyy-MM-dd" style="width: 100%" type="date"
                placeholder="请选择立项时间" :disabled="!canEdit" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目状态" prop="status">
              <el-select v-model="form.status" placeholder="项目状态" clearable style="width: 100%" :disabled="!canEdit">
                <el-option v-for="dict in manage_project_status" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="期望交付时间" prop="deliveryDate">
              <el-date-picker v-model="form.expectDeliveryTime" value-format="yyyy-MM-dd" style="width: 100%"
                type="date" placeholder="请选择期望交付时间" :disabled="isDeliveryDisabled" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目金额(万)" prop="amount">
              <el-input-number v-model="form.amount" placeholder="请输入项目金额" controls-position="right" style="width: 100%"
                :min="0" :precision="2" :disabled="isAmountDisabled" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目进度" prop="progress">
              <el-input-number v-model="form.progress" placeholder="请输入项目进度" controls-position="right"
                style="width: 100%" :min="0" :max="100" :disabled="isProgressDisabled" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="currentNodeCode === 'project-development'">
          <el-col :span="24">
            <el-form-item label="周报记录">
              <el-table :data="weeklyReports" border style="width: 100%">
                <el-table-column prop="title" label="标题" />
                <el-table-column prop="fllingDate" label="填报日期" width="120">
                  <template #default="scope">
                    <span>{{ parseTime(scope.row.fllingDate, '{y}-{m}-{d}') }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="content" label="内容" show-overflow-tooltip />
                <el-table-column label="操作" width="100">
                  <template #default="scope">
                    <el-button link type="primary" size="small" @click="viewWeeklyReport(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
              </el-table>

              <el-button type="primary" plain @click="addWeeklyReport" class="mt-2"
                v-if="canAddWeeklyReport">添加周报</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 审核操作区域 -->
    <el-card v-if="showAuditSection">
      <template #header>
        <span class="card-title">审核操作</span>
      </template>

      <el-form :model="auditForm" label-width="120px">
        <el-form-item label="审核意见" prop="opinion">
          <el-input v-model="auditForm.opinion" type="textarea" :rows="4" placeholder="请输入审核意见" />
        </el-form-item>

        <el-form-item label="审核附件" prop="attachment">
          <el-upload class="upload-demo" drag action="#" multiple :file-list="auditForm.attachmentList"
            :before-upload="beforeUpload" :on-remove="handleRemove">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮 -->
    <div class="operation-buttons">
      <el-button type="success" @click="handleApprove" v-if="canApprove" :loading="submitting">
        通过审核
      </el-button>
      <el-button type="warning" @click="handleReject" v-if="canReject" :loading="submitting">
        驳回
      </el-button>
      <el-button type="primary" @click="handleSubmit" v-if="canSubmit" :loading="submitting">
        提交
      </el-button>
      <el-button @click="handleCancel">取消</el-button>
    </div>

    <!-- 周报详情对话框 -->
    <el-dialog title="周报详情" v-model="weeklyReportDialog.visible" width="60%">
      <div v-if="weeklyReportDialog.data">
        <p><strong>标题：</strong>{{ weeklyReportDialog.data.title }}</p>
        <p><strong>填报日期：</strong>{{ parseTime(weeklyReportDialog.data.fllingDate, '{y}-{m}-{d}') }}</p>
        <p><strong>内容：</strong></p>
        <div v-html="weeklyReportDialog.data.content" class="weekly-report-content"></div>
      </div>
    </el-dialog>

    <!-- 周报编辑对话框 -->
    <el-dialog title="添加周报" v-model="weeklyReportEditDialog.visible" width="70%">
      <el-form ref="weeklyReportFormRef" :model="weeklyReportForm" :rules="weeklyReportRules" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="weeklyReportForm.title" placeholder="请输入周报标题" />
        </el-form-item>

        <el-form-item label="填报日期" prop="fllingDate">
          <el-date-picker v-model="weeklyReportForm.fllingDate" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择填报日期" style="width: 100%" />
        </el-form-item>

        <el-form-item label="周报内容" prop="content">
          <editor v-model="weeklyReportForm.content" :min-height="200" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="weeklyReportEditDialog.visible = false">取消</el-button>
        <el-button type="primary" @click="saveWeeklyReport" :loading="weeklyReportSubmitting">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { ref, computed, onMounted, reactive } from 'vue';
import { useRoute } from 'vue-router';
import { deptTreeSelect } from '@/api/system/user/index';
import { listProjectDemandWeeklyReport, addProjectDemandWeeklyReport } from '@/api/system/projectDemandWeeklyReport';
import { getProject, updateProject } from '@/api/system/project';
import { getProjectDemand, updateProjectDemand } from '@/api/system/projectDemand';
import { submitWorkflowTask, approveWorkflowTask, rejectWorkflowTask, completeWorkflowTask, getWorkflowInstance, getWorkflowTaskVariables } from '@/api/workflow';
import { UploadFilled } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { manage_project_status, manage_project_type } = toRefs<any>(proxy?.useDict('manage_project_status', 'manage_project_type'));

// 基础数据
const enabledDeptOptions = ref<any[]>([]);
const submitting = ref(false);
const weeklyReportSubmitting = ref(false);

// 工作流相关数据
const currentNodeCode = ref('project-audit'); // 当前节点编码，从工作流系统获取
const workflowInstanceId = ref(''); // 工作流实例ID
const projectDemandId = ref(''); // 项目需求ID

// 表单数据
const form = ref({
  id: undefined,
  name: '',
  type: '',
  deptId: '',
  initiationDate: '',
  status: '',
  expectDeliveryTime: '',
  amount: 0,
  progress: 0,
});

// 审核表单
const auditForm = ref({
  opinion: '',
  attachment: '',
  attachmentList: [] as any[]
});

// 周报相关数据
const weeklyReports = ref<any[]>([]);
const weeklyReportDialog = reactive({
  visible: false,
  data: null as any
});

const weeklyReportEditDialog = reactive({
  visible: false
});

const weeklyReportForm = ref({
  title: '',
  projectDemandId: '',
  fllingDate: '',
  content: '',
  attachment: ''
});

// 表单引用
const projectFormRef = ref();
const weeklyReportFormRef = ref();

// 权限控制计算属性
const canEdit = computed(() => {
  return currentNodeCode.value === 'demand-create';
});

const canApprove = computed(() => {
  return currentNodeCode.value === 'project-audit' || currentNodeCode.value === 'project-final-audit';
});

const canReject = computed(() => {
  return currentNodeCode.value === 'project-audit' || currentNodeCode.value === 'project-final-audit';
});

const canSubmit = computed(() => {
  return currentNodeCode.value === 'demand-create' || currentNodeCode.value === 'project-development';
});

const showAuditSection = computed(() => {
  return currentNodeCode.value === 'project-audit' || currentNodeCode.value === 'project-final-audit';
});

const canAddWeeklyReport = computed(() => {
  return currentNodeCode.value === 'project-development';
});

// 字段权限控制
const isAmountDisabled = computed(() => {
  return currentNodeCode.value !== 'project-audit' && currentNodeCode.value !== 'project-final-audit';
});

const isDeliveryDisabled = computed(() => {
  return currentNodeCode.value !== 'project-audit' && currentNodeCode.value !== 'project-final-audit';
});

const isProgressDisabled = computed(() => {
  return currentNodeCode.value !== 'project-development';
});

// 表单验证规则
const rules = {
  deptId: [
    { required: true, message: "归属区域不能为空", trigger: "blur" },
  ],
  name: [
    { required: true, message: "项目名称不能为空", trigger: "blur" },
  ],
  type: [
    { required: true, message: "项目类型不能为空", trigger: "blur" },
  ],
  status: [
    { required: true, message: "项目状态不能为空", trigger: "blur" },
  ],
  amount: [
    { required: true, message: "项目金额不能为空", trigger: "blur" },
  ],
};

const weeklyReportRules = {
  title: [
    { required: true, message: "标题不能为空", trigger: "blur" }
  ],
  fllingDate: [
    { required: true, message: "填报日期不能为空", trigger: "blur" }
  ],
  content: [
    { required: true, message: "内容不能为空", trigger: "blur" }
  ]
};

// 获取部门树
const getDeptTree = () => {
  deptTreeSelect().then((response) => {
    enabledDeptOptions.value = response.data;
    enabledDeptOptions.value = filterDisabledDept(JSON.parse(JSON.stringify(response.data)));
  });
};

/** 通过条件过滤节点  */
const filterNode = (value: string, data: any) => {
  if (!value) return true;
  return data.label.indexOf(value) !== -1;
};

// 过滤禁用的区域
const filterDisabledDept = (deptList: any[]) => {
  return deptList.filter((dept) => {
    if (dept.disabled) {
      return false;
    }
    if (dept.children && dept.children.length) {
      dept.children = filterDisabledDept(dept.children);
    }
    return true;
  });
};

// 获取周报列表
const getWeeklyReports = async () => {
  if (projectDemandId.value) {
    try {
      const response = await listProjectDemandWeeklyReport({
        projectDemandId: projectDemandId.value,
        pageNum: 1,
        pageSize: 100
      });
      weeklyReports.value = response.rows || [];
    } catch (error) {
      console.error('获取周报列表失败:', error);
    }
  }
};

// 审核操作
const handleApprove = async () => {
  try {
    submitting.value = true;

    // 先保存表单数据
    if (currentNodeCode.value === 'project-audit' || currentNodeCode.value === 'project-final-audit') {
      // 更新项目需求数据
      await updateProjectDemand({
        ...form.value,
        auditOpinion: auditForm.value.opinion,
        auditAttachment: auditForm.value.attachment
      });
    }

    // 调用工作流API进行审核通过操作
    await approveWorkflowTask({
      instanceId: workflowInstanceId.value,
      nodeCode: currentNodeCode.value,
      opinion: auditForm.value.opinion,
      attachment: auditForm.value.attachment
    });

    proxy?.$modal.msgSuccess("审核通过成功！");
    handleCancel();
  } catch (error) {
    proxy?.$modal.msgError("审核操作失败！");
  } finally {
    submitting.value = false;
  }
};

const handleReject = async () => {
  if (!auditForm.value.opinion.trim()) {
    proxy?.$modal.msgError("驳回时必须填写审核意见！");
    return;
  }

  try {
    submitting.value = true;

    // 先保存审核意见
    await updateProjectDemand({
      ...form.value,
      auditOpinion: auditForm.value.opinion,
      auditAttachment: auditForm.value.attachment
    });

    // 调用工作流API进行驳回操作
    await rejectWorkflowTask({
      instanceId: workflowInstanceId.value,
      nodeCode: currentNodeCode.value,
      opinion: auditForm.value.opinion,
      attachment: auditForm.value.attachment
    });

    proxy?.$modal.msgSuccess("驳回成功！");
    handleCancel();
  } catch (error) {
    proxy?.$modal.msgError("驳回操作失败！");
  } finally {
    submitting.value = false;
  }
};

const handleSubmit = async () => {
  try {
    await projectFormRef.value?.validate();
    submitting.value = true;

    // 根据节点类型保存不同的数据
    if (currentNodeCode.value === 'demand-create') {
      // 保存项目需求数据
      await updateProjectDemand(form.value);
    } else if (currentNodeCode.value === 'project-development') {
      // 保存项目进度数据
      await updateProject(form.value);

      // 如果进度达到100%，自动提交到终审
      if (form.value.progress >= 100) {
        await submitWorkflowTask({
          instanceId: workflowInstanceId.value,
          nodeCode: currentNodeCode.value,
          formData: form.value
        });
        proxy?.$modal.msgSuccess("项目已完成，已提交终审！");
        handleCancel();
        return;
      }
    }

    // 提交工作流任务
    await submitWorkflowTask({
      instanceId: workflowInstanceId.value,
      nodeCode: currentNodeCode.value,
      formData: form.value
    });

    proxy?.$modal.msgSuccess("提交成功！");
    handleCancel();
  } catch (error) {
    proxy?.$modal.msgError("提交失败！");
  } finally {
    submitting.value = false;
  }
};

const handleCancel = () => {
  // TODO: 返回上一页或关闭页面
  proxy?.$router.go(-1);
};

// 周报操作
const viewWeeklyReport = (row: any) => {
  weeklyReportDialog.data = row;
  weeklyReportDialog.visible = true;
};

const addWeeklyReport = () => {
  weeklyReportForm.value = {
    title: '',
    projectDemandId: projectDemandId.value,
    fllingDate: '',
    content: '',
    attachment: ''
  };
  weeklyReportEditDialog.visible = true;
};

const saveWeeklyReport = async () => {
  try {
    await weeklyReportFormRef.value?.validate();
    weeklyReportSubmitting.value = true;

    await addProjectDemandWeeklyReport(weeklyReportForm.value);
    proxy?.$modal.msgSuccess("周报保存成功！");

    weeklyReportEditDialog.visible = false;
    await getWeeklyReports(); // 刷新周报列表
  } catch (error) {
    proxy?.$modal.msgError("周报保存失败！");
  } finally {
    weeklyReportSubmitting.value = false;
  }
};

// 文件上传相关
const beforeUpload = (file: File) => {
  const isValidType = ['image/jpeg', 'image/png', 'application/pdf', 'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document'].includes(file.type);
  const isLt10M = file.size / 1024 / 1024 < 10;

  if (!isValidType) {
    proxy?.$modal.msgError('只能上传jpg/png/pdf/doc/docx格式的文件!');
    return false;
  }
  if (!isLt10M) {
    proxy?.$modal.msgError('上传文件大小不能超过 10MB!');
    return false;
  }

  // 阻止自动上传，手动处理
  return false;
};

const handleRemove = (file: any) => {
  const index = auditForm.value.attachmentList.findIndex(item => item.uid === file.uid);
  if (index > -1) {
    auditForm.value.attachmentList.splice(index, 1);
  }
};

// 通过taskId加载流程变量和项目需求数据
const loadWorkflowDataByTaskId = async (taskId: string) => {
  try {
    // 获取任务变量
    const taskVariablesResponse = await getWorkflowTaskVariables(taskId);
    const variables = taskVariablesResponse.data || {};

    // 从变量中获取projectDemandId
    const demandId = variables.projectDemandId;
    if (demandId) {
      projectDemandId.value = demandId;

      // 查询项目需求信息
      const demandData = await getProjectDemand(demandId);
      if (demandData.data) {
        // 填充表单数据
        form.value = { ...form.value, ...demandData.data };
      }
    }

    // 设置工作流实例ID（如果变量中有）
    if (variables.instanceId) {
      workflowInstanceId.value = variables.instanceId;
    }

    // 设置当前节点编码（如果变量中有）
    if (variables.nodeCode) {
      currentNodeCode.value = variables.nodeCode;
    }

  } catch (error) {
    console.error('通过taskId加载数据失败:', error);
    proxy?.$modal.msgError('加载任务数据失败！');
  }
};

// 加载数据的方法
const loadWorkflowData = async () => {
  try {
    if (workflowInstanceId.value) {
      const workflowData = await getWorkflowInstance(workflowInstanceId.value);
      currentNodeCode.value = workflowData.data.currentNodeCode;

      // 获取项目需求数据
      if (projectDemandId.value) {
        const demandData = await getProjectDemand(projectDemandId.value);
        form.value = { ...form.value, ...demandData.data };
      }
    }
  } catch (error) {
    console.error('加载工作流数据失败:', error);
    proxy?.$modal.msgError('加载数据失败！');
  }
};

// 初始化
onMounted(async () => {
  getDeptTree();

  // 从路由获取参数
  const route = useRoute();
  const taskId = route.query.taskId as string;
  workflowInstanceId.value = route.query.instanceId as string || route.query.id as string;
  projectDemandId.value = route.query.demandId as string;

  if (route.query.nodeCode) {
    currentNodeCode.value = route.query.nodeCode as string;
  }

  // 优先通过taskId加载数据
  if (taskId) {
    await loadWorkflowDataByTaskId(taskId);
  } else if (workflowInstanceId.value) {
    // 回退到原有的加载方式
    await loadWorkflowData();
  }

  if (currentNodeCode.value === 'project-development') {
    await getWeeklyReports();
  }
});
</script>

<style scoped>
.project-workflow-container {
  padding: 20px;
}

.card-title {
  font-weight: bold;
  font-size: 16px;
}

.operation-buttons {
  text-align: center;
  margin-top: 20px;
}

.operation-buttons .el-button {
  margin: 0 10px;
}

.weekly-report-content {
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 10px;
  max-height: 300px;
  overflow-y: auto;
}

.mt-2 {
  margin-top: 8px;
}

.mb-4 {
  margin-bottom: 16px;
}
</style>
