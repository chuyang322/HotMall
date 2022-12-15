import axios from '../utils/axios'

export function verification (params) {
  return axios.get(`/verification/check/${params}`)
}
