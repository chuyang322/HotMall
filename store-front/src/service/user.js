import axios from '../utils/axios'

export function login (params) {
  return axios.post('/user/login', params)
}

export function logout () {
  return axios.post('/user/logout')
}

export function updateUserInfo (params) {
  return axios.post(`/user/update`, params)
}

export function register (params) {
  return axios.post('/user/register', params)
}
export function setUpShop (params) {
  return axios.post('/shop/setUpShop', params)
}
export function getType () {
  return axios.get('/user/getType')
}
