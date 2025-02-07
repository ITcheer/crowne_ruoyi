import request from '@/utils/request'

// 获取我的维修工单列表
export function listMyMaintenanceOrder(query) {
  return request({
    url: '/homePage/myMaintenanceOrder/list',
    method: 'get',
    params: query
  })
}

// 获取我的清洁工单列表
export function listMyCleaningOrder(query) {
  return request({
    url: '/homePage/myCleaningOrder/list',
    method: 'get',
    params: query
  })
}

// 获取我的校工工单列表
export function listMyFacilityOrder(query) {
  return request({
    url: '/homePage/myFacilityOrder/list',
    method: 'get',
    params: query
  })
}

// 获取我的投诉工单列表
export function listMyComplaintOrder(query) {
  return request({
    url: '/homePage/myComplaintOrder/list',
    method: 'get',
    params: query
  })
}

// 获取全部工单列表
export function listAllOrders(query) {
  return request({
    url: '/homePage/allOrders/list',
    method: 'get',
    params: query
  })
}
