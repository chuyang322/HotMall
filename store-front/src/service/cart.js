import axios from '../utils/axios'

export function addGoods (id, number) {
  return axios.get(`/cart/add/${id}/${number}`)
}
export function getGoodsByUserId () {
  return axios.get(`/cart/list`)
}
export function deleteById (id) {
  return axios.get(`/cart/delete/${id}`)
}
export function updateNumber (id, number) {
  return axios.get(`/cart/update/${id}/${number}`)
}
