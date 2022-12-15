import axios from '../utils/axios'

export function getMyGoodsList (current, size) {
  return axios.get(`/shop/list/${current}/${size}`)
}
export function getMyGoodsByKeyword (current, size, keyword) {
  return axios.get(`/shop/list/${current}/${size}/${keyword}`)
}
export function getMyShop () {
  return axios.get('/shop/myShop')
}
export function deleteById (id) {
  return axios.get(`/shop/deleteById/${id}`)
}
export function deleteByIds (obj) {
  return axios.post(`/shop/deleteByIds`, obj)
}
export function updateGoods (obj) {
  return axios.post(`/shop/updateGoods`, obj)
}
export function saveGoods (obj) {
  return axios.post(`/shop/saveGoods`, obj)
}
