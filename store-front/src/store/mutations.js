export default {
  setIsLogin (state, payload) {
    state.isLogin = payload.isLogin
  },
  setShowLogin (state, payload) {
    state.showLogin = payload.showLogin
  },
  setShowRegister (state, payload) {
    state.showRegister = payload.showRegister
  },
  setShowAddressCard (state, payload) {
    state.showAddressCard = payload.showAddressCard
  },
  setShowAddressEditor (state, payload) {
    state.showAddressEditor = payload.showAddressEditor
  }
}
