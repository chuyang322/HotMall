import axios from '../utils/axios'

export function uploadImage (param) {
  return axios.post(`/images/upload`, param)
}
