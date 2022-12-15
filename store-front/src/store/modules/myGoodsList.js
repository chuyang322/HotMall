const state = {
  list: [],
  current: 1,
  pages: 0,
  total: 0,
  keyword: '',
  pageSize: 10
}

const getters = {}

const mutations = {
  setList (state, { list }) {
    state.list = list
  },
  setPages (state, { pages }) {
    state.pages = pages
  },
  setTotal (state, { total }) {
    state.total = total
  },
  setCurrent (state, { current }) {
    state.current = current
  },
  setKeyword (state, { keyword }) {
    state.keyword = keyword
  }
}

const actions = {
  updateList (content, { list }) {
    content.commit('setList', { list })
  },
  updatePages (content, { pages }) {
    content.commit('setPages', { pages })
  },
  updateTotal (content, { total }) {
    content.commit('setTotal', { total })
  },
  updateCurrent (content, { current }) {
    content.commit('setCurrent', { current })
  },
  updateKeyword (content, { keyword }) {
    content.commit('setKeyword', { keyword })
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
