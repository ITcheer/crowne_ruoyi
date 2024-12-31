import request from '@/utils/request'

// 查询我的维护订单列表
export function listMyMaintenanceOrder(query) {
  return request({
    url: '/maintenanceOrder/my/list',
    method: 'get',
    params: query
  })
}

// 查询维护订单详细
export function getMaintenanceOrder(issueId) {
  return request({
    url: '/maintenanceOrder/my/' + issueId,
    method: 'get'
  })
}

// 导出维护订单
export function exportMaintenanceOrder(query) {
  return request({
    url: '/maintenanceOrder/my/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 新增维护订单
export function addMaintenanceOrder(data) {
  return request({
    url: '/maintenanceOrder/my',
    method: 'post',
    data: data
  })
}

// 修改维护订单
export function updateMaintenanceOrder(data) {
  return request({
    url: '/maintenanceOrder/my',
    method: 'put',
    data: data
  })
}

// 删除维护订单
export function delMaintenanceOrder(issueId) {
  return request({
    url: '/maintenanceOrder/my/' + issueId,
    method: 'delete'
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/maintenanceOrder/my/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/maintenanceOrder/my/profile',
    method: 'get'
  })
}

// 用部门id查询用户
export function listUserByDeptId(deptId) {
  return request({
    url: '/maintenanceOrder/my/dept/' + deptId,
    method: 'get'
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/maintenanceOrder/my/user/' + userId,
    method: 'get'
  })
}
