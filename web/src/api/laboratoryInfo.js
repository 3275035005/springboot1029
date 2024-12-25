import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/laboratory-info/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/laboratory-info/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/laboratory-info/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/laboratory-info/update`,
    method: 'put',
    data: data
  })
}

export function getInfoId(id){
  return request({
    url: `/laboratory-info/getInfoId/`+id,
    method: 'get'
  })
}

export function getLaboratoryInfoByAppointmentId(id){
  return request({
    url: `/laboratory-info/getLaboratoryInfoByAppointmentId/`+id,
    method: 'get'
  })
}


export function sendPaper(userId,appointmentId, data){
  return request({
    url: `/laboratory-info/sendPaper/`+userId+'/'+appointmentId,
    method: 'post',
    data: data
  })
}
export function sendScore(data){
  return request({
    url: `/laboratory-info/sendScore`,
    method: 'post',
    data: data
  })
}
