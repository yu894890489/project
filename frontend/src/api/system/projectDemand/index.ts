import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ProjectDemandVO, ProjectDemandForm, ProjectDemandQuery } from '@/api/system/projectDemand/types';

/**
 * 查询需求管理列表
 * @param query
 * @returns {*}
 */

export const listProjectDemand = (query?: ProjectDemandQuery): AxiosPromise<ProjectDemandVO[]> => {
  return request({
    url: '/system/projectDemand/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询需求管理详细
 * @param id
 */
export const getProjectDemand = (id: string | number): AxiosPromise<ProjectDemandVO> => {
  return request({
    url: '/system/projectDemand/' + id,
    method: 'get'
  });
};

/**
 * 新增需求管理
 * @param data
 */
export const addProjectDemand = (data: ProjectDemandForm) => {
  return request({
    url: '/system/projectDemand',
    method: 'post',
    data: data
  });
};

/**
 * 修改需求管理
 * @param data
 */
export const updateProjectDemand = (data: ProjectDemandForm) => {
  return request({
    url: '/system/projectDemand',
    method: 'put',
    data: data
  });
};

/**
 * 删除需求管理
 * @param id
 */
export const delProjectDemand = (id: string | number | Array<string | number>) => {
  return request({
    url: '/system/projectDemand/' + id,
    method: 'delete'
  });
};
