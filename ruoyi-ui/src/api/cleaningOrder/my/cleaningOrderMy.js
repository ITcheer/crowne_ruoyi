import request from '@/utils/request'

// 查询我的清洁订单列表
export function listMyCleaningOrder(query) {
  return request({
    url: '/cleaningOrder/my/list',
    method: 'get',
    params: query
  })
}

// 查询清洁订单详细
export function getCleaningOrder(issueId) {
  return request({
    url: '/cleaningOrder/my/' + issueId,
    method: 'get'
  })
}

// 新增清洁订单
export function addCleaningOrder(data) {
  return request({
    url: '/cleaningOrder/my',
    method: 'post',
    data: data
  })
}

// 修改清洁订单
export function updateCleaningOrder(data) {
  return request({
    url: '/cleaningOrder/my',
    method: 'put',
    data: data
  })
}

// 删除清洁订单
export function delCleaningOrder(issueId) {
  return request({
    url: '/cleaningOrder/my/' + issueId,
    method: 'delete'
  })
}

// 导出清洁订单
export function exportCleaningOrder(query) {
  return request({
    url: '/cleaningOrder/my/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/cleaningOrder/my/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 获取当前用户信息
export function getUserProfile() {
  return request({
    url: '/cleaningOrder/my/profile',
    method: 'get'
  })
}

// 根据部门id获取用户列表
export function listUserByDeptId(deptId) {
  return request({
    url: '/cleaningOrder/my/dept/' + deptId,
    method: 'get'
  })
}

// 根据用户编号获取详细信息
export function getUser(userId) {
  return request({
    url: '/cleaningOrder/my/user/' + userId,
    method: 'get'
  })
}

// 发送工单完成邮件
export function sendCompletionEmail(issueId) {
  return request({
    url: '/cleaningOrder/my/sendCompletionEmail/' + issueId,
    method: 'post'
  })
}

// 发送工单未完成邮件
export function sendIncompleteEmail(issueId) {
  return request({
    url: '/cleaningOrder/my/sendIncompleteEmail/' + issueId,
    method: 'post'
  })
}
