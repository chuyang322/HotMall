const state = {
  list: []
}

const getters = {}

const mutations = {
  setList (state, { list }) {
    state.list = list
  }
}

const actions = {
  updateList (content, { list }) {
    content.commit('setList', { list })
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
