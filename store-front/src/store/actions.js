export default {
  updateIsLogin (content, payload) {
    content.commit('setIsLogin', payload)
  },
  updateShowLogin (content, payload) {
    content.commit('setShowLogin', payload)
  },
  updateShowRegister (content, payload) {
    content.commit('setShowRegister', payload)
  },
  updateShowAddressCard (content, payload) {
    content.commit('setShowAddressCard', payload)
  },
  updateShowAddressEditor (content, payload) {
    content.commit('setShowAddressEditor', payload)
  }
}
