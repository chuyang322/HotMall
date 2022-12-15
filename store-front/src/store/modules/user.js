const state = {
  id: undefined,
  account: undefined,
  name: undefined,
  createTime: undefined,
  email: undefined,
  phone: undefined,
  avatar: undefined,
  gender: undefined,
  age: undefined,
  type: undefined
}

const getters = {}

const mutations = {
  setUser (state, { user }) {
    state.id = user.id
    state.account = user.account
    state.name = user.name
    state.createTime = user.createTime
    state.email = user.email
    state.phone = user.phone
    state.gender = user.gender
    state.avatar = user.avatar
    state.age = user.age
    state.type = user.type
    localStorage.setItem('user', JSON.stringify(user))
  },
  setId (state, { id }) {
    state.id = id
  },
  setAccount (state, { account }) {
    state.account = account
  },
  setName (state, { name }) {
    state.name = name
  },
  setCreateTime (state, { createTime }) {
    state.createTime = createTime
  },
  setEmail (state, { email }) {
    state.email = email
  },
  setPhone (state, { phone }) {
    state.phone = phone
  },
  setGender (state, { gender }) {
    state.gender = gender
  },
  setAvatar (state, { avatar }) {
    state.avatar = avatar
  },
  setType (state, { type }) {
    state.type = type
  }
}

const actions = {
  updateUser (content, payload) {
    content.commit('setUser', payload)
  },
  updateId (content, payload) {
    content.commit('setId', payload)
  },
  updateAccount (content, payload) {
    content.commit('setAccount', payload)
  },
  updateName (content, payload) {
    content.commit('setName', payload)
  },
  updateCreateTime (content, payload) {
    content.commit('setCreateTime', payload)
  },
  updateEmail (content, payload) {
    content.commit('setEmail', payload)
  },
  updatePhone (content, payload) {
    content.commit('setPhone', payload)
  },
  updateSex (content, payload) {
    content.commit('setSex', payload)
  },
  updateAvatar (content, payload) {
    content.commit('setAvatar', payload)
  },
  updateType (content, payload) {
    content.commit('setType', payload)
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
