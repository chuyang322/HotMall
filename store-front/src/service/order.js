import axios from '../utils/axios'

export function createOrder (list, addressId) {
  return axios.post(`/order/create?addressId=${addressId}`, list)
}

export function getOrderList () {
  return axios.get(`/order/list`)
}
export function getMyOrders () {
  return axios.get(`/order/myOrders`)
}
export function updateState (id) {
  return axios.get(`/order/updateState/${id}`)
}
export function confirmReceiptGoodsById (id) {
  return axios.get(`/orderGoods/confirmReceipt/${id}`)
}
