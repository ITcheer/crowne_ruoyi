import request from '@/utils/request'

// 查询投诉订单列表
export function listComplaintOrder(query) {
  return request({
    url: '/complaintOrder/all/list',
    method: 'get',
    params: query
  })
}

// 查询投诉订单详细
export function getComplaintOrder(issueId) {
  return request({
    url: '/complaintOrder/all/' + issueId,
    method: 'get'
  })
}

// 新增投诉订单
export function addComplaintOrder(data) {
  return request({
    url: '/complaintOrder/all',
    method: 'post',
    data: data
  })
}

// 修改投诉订单
export function updateComplaintOrder(data) {
  return request({
    url: '/complaintOrder/all',
    method: 'put',
    data: data
  })
}

// 删除投诉订单
export function delComplaintOrder(issueId) {
  return request({
    url: '/complaintOrder/all/' + issueId,
    method: 'delete'
  })
}

// 导出投诉订单
export function exportComplaintOrder(query) {
  return request({
    url: '/complaintOrder/all/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
