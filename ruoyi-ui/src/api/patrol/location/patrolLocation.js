import request from '@/utils/request'

// 查询巡逻地点列表
export function listPatrolLocation(query) {
  return request({
    url: '/patrol/location/list',
    method: 'get',
    params: query
  })
}

// 查询巡逻地点详细
export function getPatrolLocation(locationId) {
  return request({
    url: '/patrol/location/' + locationId,
    method: 'get'
  })
}

// 新增巡逻地点
export function addPatrolLocation(data) {
  return request({
    url: '/patrol/location',
    method: 'post',
    data: data
  })
}

// 修改巡逻地点
export function updatePatrolLocation(data) {
  return request({
    url: '/patrol/location',
    method: 'put',
    data: data
  })
}

// 删除巡逻地点
export function delPatrolLocation(locationId) {
  return request({
    url: '/patrol/location/' + locationId,
    method: 'delete'
  })
}

// 导出巡逻地点
export function exportPatrolLocation(query) {
  return request({
    url: '/patrol/location/export',
    method: 'get',
    params: query
  })
}
