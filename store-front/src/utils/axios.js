import axios from 'axios'

axios.defaults.baseURL = 'api'
axios.defaults.withCredentials = true
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
axios.defaults.headers['token'] = localStorage.getItem('token') || ''
axios.defaults.headers.post['Content-Type'] = 'application/json'
axios.interceptors.response.use((res) => {
  console.log(res)
  return res.data
})

export default axios
