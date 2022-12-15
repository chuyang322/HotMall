const state = {
  CarList: [],
  CarTotal: 0
}

const getters = {}

const mutations = {
  setCarList (state, { CarList }) {
    state.CarList = CarList
  },
  setCarTotal (state, { CarTotal }) {
    state.CarTotal = CarTotal
  }
}

const actions = {
  updateCarList (content, { CarList }) {
    content.commit('setCarList', { CarList })
  },
  updateCarTotal (content, { CarTotal }) {
    content.commit('setCarTotal', { CarTotal })
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
