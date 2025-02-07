import request from '@/utils/request'

// 查询巡逻任务列表
export function listPatrolTask(query) {
  return request({
    url: '/patrol/task/list',
    method: 'get',
    params: query
  })
}

// 查询巡逻任务详细
export function getPatrolTask(taskId) {
  return request({
    url: '/patrol/task/' + taskId,
    method: 'get'
  })
}

// 新增巡逻任务
export function addPatrolTask(data) {
  return request({
    url: '/patrol/task',
    method: 'post',
    data: data
  })
}

// 修改巡逻任务
export function updatePatrolTask(data) {
  return request({
    url: '/patrol/task',
    method: 'put',
    data: data
  })
}

// 删除巡逻任务
export function delPatrolTask(taskId) {
  return request({
    url: '/patrol/task/' + taskId,
    method: 'delete'
  })
}

// 导出巡逻任务
export function exportPatrolTask(query) {
  return request({
    url: '/patrol/task/export',
    method: 'get',
    params: query
  })
}

// 上传图片到 Azure Blob
export function uploadImage(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/patrol/task/uploadImage',
    method: 'post',
    data: formData
  })
}

// 获取所有地点
export function listLocations() {
  return request({
    url: '/patrol/task/location/list',
    method: 'get'
  })
}

// 获取所有人员
export function listParticipants() {
  return request({
    url: '/patrol/task/user/list',
    method: 'get'
  })
}

// 查询巡逻任务打卡记录
export function listPatrolTaskRecords(taskId) {
  return request({
    url: `/patrol/task/${taskId}/records`,
    method: 'get'
  })
}
