
import request from '@/utils/request'

// 查询工程巡逻地点列表
export function listEngineeringPatrolLocation(query) {
  return request({
    url: '/engineering/patrolLocation/list',
    method: 'get',
    params: query
  })
}

// 查询工程巡逻地点详细
export function getEngineeringPatrolLocation(locationId) {
  return request({
    url: '/engineering/patrolLocation/' + locationId,
    method: 'get'
  })
}

// 新增工程巡逻地点
export function addEngineeringPatrolLocation(data) {
  return request({
    url: '/engineering/patrolLocation',
    method: 'post',
    data: data
  })
}

// 修改工程巡逻地点
export function updateEngineeringPatrolLocation(data) {
  return request({
    url: '/engineering/patrolLocation',
    method: 'put',
    data: data
  })
}

// 删除工程巡逻地点
export function delEngineeringPatrolLocation(locationId) {
  return request({
    url: '/engineering/patrolLocation/' + locationId,
    method: 'delete'
  })
}

// 导出工程巡逻地点
export function exportEngineeringPatrolLocation(query) {
  return request({
    url: '/engineering/patrolLocation/export',
    method: 'get',
    params: query
  })
}