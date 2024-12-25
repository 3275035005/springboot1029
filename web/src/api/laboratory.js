import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/laboratory/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/laboratory/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data) {
  return request({
    url: `/laboratory/insert`,
    method: 'post',
    data: data
  })
}
export function update(data) {
  return request({
    url: `/laboratory/update`,
    method: 'put',
    data: data
  })
}
export function getLaboratoryAll(){
  return request({
    url: `/laboratory/getLaboratoryAll`,
    method: 'get'
  })
}
export function getLaboratoryById(id) {
  return request({
    url: `/laboratory/getLaboratoryById/` + id,
    method: 'get'
  })
}
export function getLaboratoryProcedureById(id) {
  return request({
    url: `/laboratory/getLaboratoryProcedureById/` + id,
    method: 'get'
  })
}
export function getZzt(id) {
  return request({
    url: `/laboratory/getZzt/`+id,
    method: 'get'
  })
}

