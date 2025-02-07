import request from '@/utils/request'

// 查询工程巡逻任务列表
export function listEngineeringPatrolTask(query) {
  return request({
    url: '/engineering/patrolTask/list',
    method: 'get',
    params: query
  })
}

// 查询工程巡逻任务详细
export function getEngineeringPatrolTask(taskId) {
  return request({
    url: '/engineering/patrolTask/' + taskId,
    method: 'get'
  })
}

// 新增工程巡逻任务
export function addEngineeringPatrolTask(data) {
  return request({
    url: '/engineering/patrolTask',
    method: 'post',
    data: data
  })
}

// 修改工程巡逻任务
export function updateEngineeringPatrolTask(data) {
  return request({
    url: '/engineering/patrolTask',
    method: 'put',
    data: data
  })
}

// 删除工程巡逻任务
export function delEngineeringPatrolTask(taskId) {
  return request({
    url: '/engineering/patrolTask/' + taskId,
    method: 'delete'
  })
}

// 导出工程巡逻任务
export function exportEngineeringPatrolTask(query) {
  return request({
    url: '/engineering/patrolTask/export',
    method: 'get',
    params: query
  })
}

// 获取所有地点
export function listEngineeringPatrolLocation() {
  return request({
    url: '/engineering/patrolTask/location/list',
    method: 'get'
  })
}

// 获取所有人员
export function listParticipants() {
  return request({
    url: '/engineering/patrolTask/user/list',
    method: 'get'
  })
}

// 查询工程巡逻任务打卡记录
export function listPatrolTaskRecords(taskId) {
  return request({
    url: `/engineering/patrolTask/${taskId}/records`,
    method: 'get'
  });
}
