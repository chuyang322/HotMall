import axios from '../utils/axios'

export function getAddress () {
  return axios.get(`/address/list`)
}
export function addAddress (params) {
  return axios.post(`/address/add`, params)
}
export function updateAddress (params) {
  return axios.post(`/address/update`, params)
}
export function deleteAddress (id) {
  return axios.get(`/address/delete/${id}`)
}
