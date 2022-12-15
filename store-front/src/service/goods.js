import axios from '../utils/axios'

export function getGoodsByKeyword (current, size, keyword) {
  return axios.get(`/goods/list/${current}/${size}/${keyword}`)
}
export function getGoodsList (current, size) {
  return axios.get(`/goods/list/${current}/${size}`)
}
export function getSortList (current, size, sort) {
  return axios.get(`/goods/sort/${current}/${size}/${sort}`)
}
