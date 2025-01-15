import request from '@/utils/request'

// 查询个人消息通知列表
export function listPersonalMessageNotification(query) {
  return request({
    url: '/notification/personal/list',
    method: 'get',
    params: query
  })
}

// 查询个人消息通知详细
export function getPersonalMessageNotification(notificationId) {
  return request({
    url: '/notification/personal/' + notificationId,
    method: 'get'
  })
}

// 新增个人消息通知
export function addPersonalMessageNotification(data) {
  return request({
    url: '/notification/personal',
    method: 'post',
    data: data
  })
}

// 修改个人消息通知
export function updatePersonalMessageNotification(data) {
  return request({
    url: '/notification/personal',
    method: 'put',
    data: data
  })
}

// 删除个人消息通知
export function delPersonalMessageNotification(notificationId) {
  return request({
    url: '/notification/personal/' + notificationId,
    method: 'delete'
  })
}

// 根据用户ID查询个人消息通知列表
export function listPersonalMessageNotificationByUserId(userId) {
  return request({
    url: '/notification/personal/user/' + userId,
    method: 'get'
  })
}
