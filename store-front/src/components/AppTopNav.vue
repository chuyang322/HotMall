<template>
  <div class="top-nav">
    <v-login></v-login>
    <v-register></v-register>
    <div class="w">
      <div v-if="!(isLogin)">
        您还没有登录HotMall,去
        <el-button type="text" @click="login"><i class="el-icon-switch-button"></i>登录/注册</el-button>
      </div>
      <div v-else class="user">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            <img :src="user.avatar" class="avatar">
            {{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">

            <el-dropdown-item icon="el-icon-user">
              <router-link to="/ModifyInfo">{{ user.name }}</router-link>
            </el-dropdown-item>
            <router-link to="/Car"><el-dropdown-item divided icon="el-icon-shopping-cart-2">购物车</el-dropdown-item></router-link>
            <router-link to="/MyMall"><el-dropdown-item icon="el-icon-s-shop" v-if="Uncreate">我的店铺</el-dropdown-item></router-link>
            <router-link to="/MyOrder"><el-dropdown-item icon="el-icon-tickets">我的订单</el-dropdown-item></router-link>
<el-dropdown-item divided icon="el-icon-switch-button" command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import loginVue from './Login'
import registerVue from './Register'
import {mapActions, mapState} from 'vuex'
import axios from '../utils/axios'
import {getType} from '../service/user'

export default {
  name: 'AppTopNav',
  components: {
    'v-login': loginVue,
    'v-register': registerVue
  },
  data () {
    return {
      Uncreate: false
    }
  },
  computed: {
    ...mapState([
      'showLogin',
      'user',
      'isLogin'
    ])
  },
  methods: {
    ...mapActions([
      'updateShowLogin',
      'user/updateUser',
      'updateIsLogin'
    ]),
    login: function () {
      this.updateShowLogin({showLogin: true})
    },
    handleCommand: function (command) {
      if (command === 'logout') {
        this.logout()
        this.$message('成功退出')
      }
    },
    logout: function () {
      this['user/updateUser']({user: {}})
      this.updateIsLogin({islogin: false})
      axios.defaults.headers['token'] = ''
      localStorage.setItem('token', '')
    },
    getUserType: async function () {
      let data = await getType()
      let type = data.data.type
      if (type !== 0) {
        this.Uncreate = true
      }
    }
  },
  created () {
    this.getUserType()
  }
}
</script>

<style scoped>
.top-nav {
  background-color: #2b2b2b;
}

.top-nav ol {
  float: left;
  height: 51px;
  line-height: 50px;
}

.top-nav ol li {
  float: left;
  color: white;
}

.top-nav .user {
  display: flex;
  height: 51px;
  line-height: 50px;
  justify-content: flex-end;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.avatar{
  height: 32px;
  width: 32px;
  border-radius: 100%;
}
</style>
