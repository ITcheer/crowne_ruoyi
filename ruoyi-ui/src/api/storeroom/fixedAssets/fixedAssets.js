import request from '@/utils/request'

// 查询固定资产列表
export function listFixedAssets(query) {
  return request({
    url: '/storeroom/fixedAssets/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产详细
export function getFixedAssets(batchId) {
  return request({
    url: '/storeroom/fixedAssets/' + batchId,
    method: 'get'
  })
}

// 新增固定资产
export function addFixedAssets(data) {
  return request({
    url: '/storeroom/fixedAssets',
    method: 'post',
    data: data
  })
}

// 修改固定资产
export function updateFixedAssets(data) {
  return request({
    url: '/storeroom/fixedAssets',
    method: 'put',
    data: data
  })
}

// 删除固定资产
export function delFixedAssets(batchId) {
  return request({
    url: '/storeroom/fixedAssets/' + batchId,
    method: 'delete'
  })
}

// 导出固定资产
export function exportFixedAssets(query) {
  return request({
    url: '/storeroom/fixedAssets/export',
    method: 'get',
    params: query
  })
}

// 上传固定资产图片到 Azure Blob
export function uploadFixedAssetsImage(data) {
  return request({
    url: '/storeroom/fixedAssets/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 查询当前固定资产的出入库信息
export function getInventoryRecords(batchId) {
  return request({
    url: '/storeroom/fixedAssets/inventoryRecords/' + batchId,
    method: 'get'
  })
}

// 出入库操作
export function inventory(data) {
  return request({
    url: '/storeroom/fixedAssets/inventory',
    method: 'post',
    data: data
  })
}
