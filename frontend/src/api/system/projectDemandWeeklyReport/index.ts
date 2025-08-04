import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ProjectDemandWeeklyReportVO, ProjectDemandWeeklyReportForm, ProjectDemandWeeklyReportQuery } from '@/api/system/projectDemandWeeklyReport/types';

/**
 * 查询项目周报列表
 * @param query
 * @returns {*}
 */

export const listProjectDemandWeeklyReport = (query?: ProjectDemandWeeklyReportQuery): AxiosPromise<ProjectDemandWeeklyReportVO[]> => {
  return request({
    url: '/system/projectDemandWeeklyReport/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询项目周报详细
 * @param id
 */
export const getProjectDemandWeeklyReport = (id: string | number): AxiosPromise<ProjectDemandWeeklyReportVO> => {
  return request({
    url: '/system/projectDemandWeeklyReport/' + id,
    method: 'get'
  });
};

/**
 * 新增项目周报
 * @param data
 */
export const addProjectDemandWeeklyReport = (data: ProjectDemandWeeklyReportForm) => {
  return request({
    url: '/system/projectDemandWeeklyReport',
    method: 'post',
    data: data
  });
};

/**
 * 修改项目周报
 * @param data
 */
export const updateProjectDemandWeeklyReport = (data: ProjectDemandWeeklyReportForm) => {
  return request({
    url: '/system/projectDemandWeeklyReport',
    method: 'put',
    data: data
  });
};

/**
 * 删除项目周报
 * @param id
 */
export const delProjectDemandWeeklyReport = (id: string | number | Array<string | number>) => {
  return request({
    url: '/system/projectDemandWeeklyReport/' + id,
    method: 'delete'
  });
};
