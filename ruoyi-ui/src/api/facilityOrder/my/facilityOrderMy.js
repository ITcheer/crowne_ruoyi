import request from '@/utils/request'

// 查询我的校工工单列表
export function listFacilityOrder(query) {
  return request({
    url: '/facilityOrder/my/list',
    method: 'get',
    params: query
  })
}

// 导出我的校工工单
export function exportFacilityOrder(query) {
  return request({
    url: '/facilityOrder/my/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 获取我的校工工单详细信息
export function getFacilityOrder(issueId) {
  return request({
    url: '/facilityOrder/my/' + issueId,
    method: 'get'
  })
}

// 新增我的校工工单
export function addFacilityOrder(data) {
  return request({
    url: '/facilityOrder/my',
    method: 'post',
    data: data
  })
}

// 修改我的校工工单
export function updateFacilityOrder(data) {
  return request({
    url: '/facilityOrder/my',
    method: 'put',
    data: data
  })
}

// 删除我的校工工单
export function delFacilityOrder(issueIds) {
  return request({
    url: '/facilityOrder/my/' + issueIds,
    method: 'delete'
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/facilityOrder/my/uploadImage',
    method: 'post',
    data: data
  })
}

// 发送校工工单完成邮件
export function sendCompletionEmail(issueId) {
  return request({
    url: '/facilityOrder/my/sendCompletionEmail/' + issueId,
    method: 'post'
  })
}

// 发送校工工单未完成邮件
export function sendIncompleteEmail(issueId) {
  return request({
    url: '/facilityOrder/my/sendIncompleteEmail/' + issueId,
    method: 'post'
  })
}

// 获取当前用户信息
export function getUserProfile() {
  return request({
    url: '/facilityOrder/my/profile',
    method: 'get'
  })
}

// 根据部门id获取用户列表
export function listUserByDeptId(deptId) {
  return request({
    url: '/facilityOrder/my/dept/' + deptId,
    method: 'get'
  })
}

// 根据用户编号获取详细信息
export function getUser(userId) {
  return request({
    url: '/facilityOrder/my/user/' + userId,
    method: 'get'
  })
}
