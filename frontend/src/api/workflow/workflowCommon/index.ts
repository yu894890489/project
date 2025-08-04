import { RouterJumpVo } from '@/api/workflow/workflowCommon/types';
import router from '@/router';
import { createCustomNameComponent } from '@/utils/createCustomNameComponent';

// 动态引入views下所有vue文件
const modules = import.meta.glob('@/views/**/*.vue');

function loadView(view, name) {
  let res;
  for (const path in modules) {
    // 兼容 @/views/xxx.vue 或 /project/workflow/project 这类路径
    let dir = path.replace(/^.*\/views\//, '').replace(/\.vue$/, '');
    if (dir === view.replace(/^\//, '')) {
      res = createCustomNameComponent(modules[path], { name });
      return res;
    }
  }
  return res;
}

export default {
  async routerJump(routerJumpVo: RouterJumpVo, proxy) {
    proxy.$tab.closePage(proxy.$route);
    // 检查路由是否已存在
    const exist = router.getRoutes().some(r => r.path === routerJumpVo.formPath);
    if (!exist) {
      router.addRoute({
        path: routerJumpVo.formPath,
        component: loadView(routerJumpVo.formPath.replace(/^\//, ''), 'DynamicForm'),
        meta: { title: '动态表单' },
      });
    }
    // 路由跳转到表单页面
    proxy.$router.push({
      path: routerJumpVo.formPath,
      query: {
        id: routerJumpVo.businessId,
        type: routerJumpVo.type,
        taskId: routerJumpVo.taskId
      }
    });
  }
};
