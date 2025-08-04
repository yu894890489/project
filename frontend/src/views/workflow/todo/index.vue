<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div v-show="showSearch" class="mb-[10px]">
        <el-card shadow="hover">
          <el-form ref="queryFormRef" :model="queryParams" :inline="true">
            <el-form-item label="流程名称" prop="flowName">
              <el-input v-model="queryParams.flowName" placeholder="请输入流程名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="节点名称" prop="nodeName">
              <el-input v-model="queryParams.nodeName" placeholder="请输入节点名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime">
              <el-date-picker 
                v-model="queryParams.createTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD"
                @change="handleQuery"
              />
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
            <el-tag type="info" size="large">
              <el-icon><Bell /></el-icon>
              待办任务 ({{ total }})
            </el-tag>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" border :data="todoList">
        <el-table-column label="流程名称" align="center" prop="flowName" min-width="150" />
        <el-table-column label="当前节点" align="center" prop="nodeName" min-width="120" />
        <el-table-column label="项目名称" align="center" prop="projectName" min-width="150" show-overflow-tooltip />
        <el-table-column label="需求名称" align="center" prop="demandName" min-width="150" show-overflow-tooltip />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="待办时长" align="center" prop="duration" width="120">
          <template #default="scope">
            <el-tag :type="getDurationTagType(scope.row.duration)">
              {{ formatDuration(scope.row.duration) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="紧急程度" align="center" prop="priority" width="100">
          <template #default="scope">
            <el-tag :type="getPriorityTagType(scope.row.priority)" size="small">
              {{ getPriorityText(scope.row.priority) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
          <template #default="scope">
            <el-tooltip content="处理" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleProcess(scope.row)" 
                v-hasPermi="['workflow:task:handle']">处理</el-button>
            </el-tooltip>
            <el-tooltip content="查看详情" placement="top">
              <el-button link type="info" icon="View" @click="handleView(scope.row)"
                v-hasPermi="['workflow:instance:query']">详情</el-button>
            </el-tooltip>
            <el-tooltip content="查看历史" placement="top">
              <el-button link type="warning" icon="Clock" @click="handleHistory(scope.row)"
                v-hasPermi="['workflow:history:query']">历史</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" 
        v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>

    <!-- 历史记录对话框 -->
    <el-dialog title="流程历史记录" v-model="historyDialog.visible" width="60%">
      <el-timeline>
        <el-timeline-item 
          v-for="(item, index) in historyDialog.data" 
          :key="index"
          :color="getHistoryItemColor(item.action)"
          :timestamp="parseTime(item.handleTime, '{y}-{m}-{d} {h}:{i}:{s}')"
        >
          <el-card shadow="hover">
            <div class="history-item-header">
              <span class="node-name">{{ item.nodeName }}</span>
              <el-tag :type="getActionTagType(item.action)" size="small">{{ item.action }}</el-tag>
            </div>
            <div class="history-item-content">
              <p><strong>处理人：</strong>{{ item.handler }}</p>
              <p v-if="item.opinion"><strong>处理意见：</strong>{{ item.opinion }}</p>
            </div>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-dialog>
  </div>
</template>

<script setup name="WorkflowTodo" lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { getTodoTasks, getWorkflowHistory } from '@/api/workflow';
import { Bell, Edit, View, Clock } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const loading = ref(true);
const showSearch = ref(true);
const total = ref(0);

const todoList = ref<any[]>([]);

const queryFormRef = ref<ElFormInstance>();

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  flowName: undefined,
  nodeName: undefined,
  createTime: undefined
});

// 历史记录对话框
const historyDialog = reactive({
  visible: false,
  data: [] as any[]
});

/** 查询待办任务列表 */
const getList = async () => {
  loading.value = true;
  try {
    const response = await getTodoTasks(queryParams.value);
    todoList.value = response.data?.rows || [];
    total.value = response.data?.total || 0;
  } catch (error) {
    proxy?.$modal.msgError('获取待办任务列表失败！');
  } finally {
    loading.value = false;
  }
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
};

/** 处理任务 */
const handleProcess = (row: any) => {
  // 跳转到工作流处理页面
  proxy?.$router.push({
    path: '/project/workflow/project',
    query: {
      instanceId: row.instanceId,
      nodeCode: row.nodeCode,
      demandId: row.demandId,
      projectId: row.projectId
    }
  });
};

/** 查看详情 */
const handleView = (row: any) => {
  // 跳转到详情查看页面
  proxy?.$router.push({
    path: '/workflow/instance/detail',
    query: {
      instanceId: row.instanceId
    }
  });
};

/** 查看历史 */
const handleHistory = async (row: any) => {
  try {
    const response = await getWorkflowHistory(row.instanceId);
    historyDialog.data = response.data?.history || [];
    historyDialog.visible = true;
  } catch (error) {
    proxy?.$modal.msgError('获取历史记录失败！');
  }
};

/** 格式化持续时长 */
const formatDuration = (createTime: string) => {
  if (!createTime) return '-';
  
  const now = new Date();
  const create = new Date(createTime);
  const diffMs = now.getTime() - create.getTime();
  const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));
  const diffHours = Math.floor((diffMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  
  if (diffDays > 0) {
    return `${diffDays}天${diffHours}小时`;
  } else if (diffHours > 0) {
    return `${diffHours}小时`;
  } else {
    const diffMinutes = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));
    return `${diffMinutes}分钟`;
  }
};

/** 获取持续时长标签类型 */
const getDurationTagType = (createTime: string) => {
  if (!createTime) return '';
  
  const now = new Date();
  const create = new Date(createTime);
  const diffHours = (now.getTime() - create.getTime()) / (1000 * 60 * 60);
  
  if (diffHours > 72) return 'danger';   // 超过3天
  if (diffHours > 24) return 'warning';  // 超过1天
  return 'success';                      // 1天内
};

/** 获取优先级标签类型 */
const getPriorityTagType = (priority: string) => {
  switch (priority) {
    case 'HIGH': return 'danger';
    case 'MEDIUM': return 'warning';
    case 'LOW': return 'info';
    default: return '';
  }
};

/** 获取优先级文本 */
const getPriorityText = (priority: string) => {
  switch (priority) {
    case 'HIGH': return '高';
    case 'MEDIUM': return '中';
    case 'LOW': return '低';
    default: return '-';
  }
};

/** 获取历史记录项颜色 */
const getHistoryItemColor = (action: string) => {
  switch (action) {
    case '提交': return '#409EFF';
    case '通过': return '#67C23A';
    case '驳回': return '#F56C6C';
    case '完成': return '#909399';
    default: return '#409EFF';
  }
};

/** 获取操作标签类型 */
const getActionTagType = (action: string) => {
  switch (action) {
    case '提交': return 'primary';
    case '通过': return 'success';
    case '驳回': return 'danger';
    case '完成': return 'info';
    default: return 'primary';
  }
};

onMounted(() => {
  getList();
});
</script>

<style scoped>
.history-item-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.node-name {
  font-weight: bold;
  font-size: 14px;
}

.history-item-content p {
  margin: 4px 0;
  font-size: 13px;
}
</style>