import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/laboratory-appointment/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function pageQueryTeacher(page, limit, data) {
  return request({
    url: `/laboratory-appointment/pageQueryTeacher/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function appointment(data) {
  return request({
    url: `/laboratory-appointment/appointment`,
    method: 'post',
    data: data
  })
}
export function sendLaboratory(data) {
  return request({
    url: `/laboratory-appointment/sendLaboratory`,
    method: 'post',
    data: data
  })
}

export function sendLaboratoryScore(data) {
  return request({
    url: `/laboratory-appointment/sendLaboratoryScore`,
    method: 'post',
    data: data
  })
}

export function deleteAssess(id) {
  return request({
    url: `/laboratory-appointment/deleteAssess/`+id,
    method: 'post'
  })
}

export function cancelAppointment(id) {
  return request({
    url: `/laboratory-appointment/cancelAppointment/`+id,
    method: 'post'
  })
}
