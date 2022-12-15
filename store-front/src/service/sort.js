import axios from '../utils/axios'

export function allSort (current, size) {
  return axios.get(`/sort/allSort/${current}/${size}`)
}
