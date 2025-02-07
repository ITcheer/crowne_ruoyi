import request from '@/utils/request'

// 查询校工订单列表
export function listFacilityOrder(query) {
  return request({
    url: '/facilityOrder/all/list',
    method: 'get',
    params: query
  })
}

// 查询校工订单详细
export function getFacilityOrder(issueId) {
  return request({
    url: '/facilityOrder/all/' + issueId,
    method: 'get'
  })
}

// 新增校工订单
export function addFacilityOrder(data) {
  return request({
    url: '/facilityOrder/all',
    method: 'post',
    data: data
  })
}

// 修改校工订单
export function updateFacilityOrder(data) {
  return request({
    url: '/facilityOrder/all',
    method: 'put',
    data: data
  })
}

// 删除校工订单
export function delFacilityOrder(issueId) {
  return request({
    url: '/facilityOrder/all/' + issueId,
    method: 'delete'
  })
}

// 导出校工订单
export function exportFacilityOrder(query) {
  return request({
    url: '/facilityOrder/all/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/facilityOrder/all/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 分配校工订单
export function assignFacilityOrder(data) {
  return request({
    url: '/facilityOrder/all/assign',
    method: 'patch',
    data: data
  })
}
