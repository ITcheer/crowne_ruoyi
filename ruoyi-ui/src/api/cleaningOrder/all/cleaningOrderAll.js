import request from '@/utils/request'

// 查询清洁订单列表
export function listCleaningOrder(query) {
  return request({
    url: '/cleaningOrder/all/list',
    method: 'get',
    params: query
  })
}

// 查询清洁订单详细
export function getCleaningOrder(issueId) {
  return request({
    url: '/cleaningOrder/all/' + issueId,
    method: 'get'
  })
}

// 新增清洁订单
export function addCleaningOrder(data) {
  return request({
    url: '/cleaningOrder/all',
    method: 'post',
    data: data
  })
}

// 修改清洁订单
export function updateCleaningOrder(data) {
  return request({
    url: '/cleaningOrder/all',
    method: 'put',
    data: data
  })
}

// 删除清洁订单
export function delCleaningOrder(issueId) {
  return request({
    url: '/cleaningOrder/all/' + issueId,
    method: 'delete'
  })
}

// 导出清洁订单
export function exportCleaningOrder(query) {
  return request({
    url: '/cleaningOrder/all/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/cleaningOrder/all/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 分配清洁订单
export function assignCleaningOrder(data) {
  return request({
    url: '/cleaningOrder/all/assign',
    method: 'patch',
    data: data
  })
}
