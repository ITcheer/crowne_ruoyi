import request from '@/utils/request'

// 查询普通货品列表
export function listGoods(query) {
  return request({
    url: '/storeroom/goods/list',
    method: 'get',
    params: query
  })
}

// 查询普通货品详细
export function getGoods(batchId) {
  return request({
    url: '/storeroom/goods/' + batchId,
    method: 'get'
  })
}

// 新增普通货品
export function addGoods(data) {
  return request({
    url: '/storeroom/goods',
    method: 'post',
    data: data
  })
}

// 修改普通货品
export function updateGoods(data) {
  return request({
    url: '/storeroom/goods',
    method: 'put',
    data: data
  })
}

// 删除普通货品
export function delGoods(batchId) {
  return request({
    url: '/storeroom/goods/' + batchId,
    method: 'delete'
  })
}

// 导出普通货品
export function exportGoods(query) {
  return request({
    url: '/storeroom/goods/export',
    method: 'get',
    params: query
  })
}

// 上传图片到 Azure Blob
export function uploadImage(data) {
  return request({
    url: '/storeroom/goods/uploadImage',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 查询当前货品的出入库信息
export function getInventoryRecords(batchId) {
  return request({
    url: '/storeroom/goods/inventoryRecords/' + batchId,
    method: 'get'
  })
}

// 出入库操作
export function inventory(data) {
  return request({
    url: '/storeroom/goods/inventory',
    method: 'post',
    data: data
  })
}
