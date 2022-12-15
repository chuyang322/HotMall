<template>
  <div>
    <AppTopNav></AppTopNav>
  <div class="header">
      <AppHeader></AppHeader>
  </div>
  <div class="main w">
      <router-view></router-view>
  </div>
    <div>
      <AppFooter></AppFooter>
    </div>
  </div>
</template>

<script>

import AppTopNav from '../components/AppTopNav'
import AppHeader from '../components/AppHeader'
import Hello from '../components/Hello'
import AppFooter from '../components/AppFooter'
import axios from '../utils/axios'
import {mapActions, mapState} from 'vuex'

export default {
  name: 'Main',
  components: {
    AppFooter,
    Hello,
    AppTopNav,
    AppHeader
  },
  computed: {
    ...mapState([
      'isLogin'
    ])
  },
  methods: {
    ...mapActions([
      'user/updateUser',
      'updateIsLogin'
    ]),
    initUserInfo: async function () {
      try {
        axios.defaults.headers['token'] = localStorage.getItem('token')
        const user = JSON.parse(localStorage.getItem('user'))
        this['user/updateUser']({ user })
        if (user.name != null) {
          this.updateIsLogin({ isLogin: true })
        }
      } catch (error) {
        console.log(error)
      }
    }
  },
  mounted () {
    this.initUserInfo()
  }
}
</script>

<style scoped>

</style>
