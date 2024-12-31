import request from '@/utils/request'

// 查询维护订单列表
export function listMaintenanceOrder(query) {
  return request({
    url: '/maintenanceOrder/all/list',
    method: 'get',
    params: query
  })
}

// 查询维护订单详细
export function getMaintenanceOrder(issueId) {
  return request({
    url: '/maintenanceOrder/all/' + issueId,
    method: 'get'
  })
}

// 新增维护订单
export function addMaintenanceOrder(data) {
  return request({
    url: '/maintenanceOrder/all',
    method: 'post',
    data: data
  })
}

// 修改维护订单
export function updateMaintenanceOrder(data) {
  return request({
    url: '/maintenanceOrder/all',
    method: 'put',
    data: data
  })
}

// 删除维护订单
export function delMaintenanceOrder(issueId) {
  return request({
    url: '/maintenanceOrder/all/' + issueId,
    method: 'delete'
  })
}

// 导出维护订单
export function exportMaintenanceOrder(query) {
  return request({
    url: '/maintenanceOrder/all/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 分配维护订单
export function assignMaintenanceOrder(data) {
  return request({
    url: '/maintenanceOrder/all/assign',
    method: 'patch',
    data: data
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/maintenanceOrder/all/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 关闭维护订单
export function closeMaintenanceOrder(data) {
  return request({
    url: '/maintenanceOrder/all/close',
    method: 'patch',
    data: data
  })
}
