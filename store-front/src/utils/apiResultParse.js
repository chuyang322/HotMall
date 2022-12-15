import {Message} from 'element-ui'
import store from '../store'
export const apiResultParse = (data) => {
  console.log(data)
  if (!data || data === '') {
    Message({
      message: '请求失败，登录已过期，请重新登录',
      type: 'error'
    })
    localStorage.setItem('user', '')
    store.commit('setIsLogin', {isLogin: false})
    return false
  } else if (data.code === 200) {
    return true
  } else if (data.code === 400) {
    Message({
      message: data.message,
      type: 'error'
    })
    return false
  }
}
