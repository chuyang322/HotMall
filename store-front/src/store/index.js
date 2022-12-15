import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions'
import mutations from './mutations'
import state from './state'
import getters from './getters'
import goods from './modules/goods'
import user from './modules/user'
import goodsList from './modules/goodsList'
import carList from './modules/carList'
import addressList from './modules/addressList'
import myGoodsList from './modules/myGoodsList'
import myOrderList from './modules/myOrderList'

Vue.use(Vuex)

export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters,
  modules: {
    goods,
    user,
    goodsList,
    myGoodsList,
    carList,
    addressList,
    myOrderList
  }
})
