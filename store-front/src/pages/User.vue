<template>
  <div>
    <div class="header">个人中心</div>
  <div class="aside">
    <router-link to="/"><img src="../assets/logo.png" style="height: 50px;margin: 30px 20px" title="返回主页"> </router-link>
    <el-menu :default-active="$router.currentRoute.path"
             class="el-menu-vertical-demo"
             :router="true"
    >
      <el-menu-item index="/ModifyInfo">
        <i class="el-icon-user"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>
      <el-menu-item index="/Car">
        <i class="el-icon-shopping-cart-2"></i>
        <span slot="title" >购物车</span>
      </el-menu-item>
      <el-submenu index="3" :disabled="Uncreate">
        <template slot="title">
          <i class="el-icon-s-shop"></i>
          <span>我的店铺</span>
        </template>
        <el-menu-item-group >
          <el-menu-item index="/MyMall"><i class="el-icon-edit-outline"></i> 商品管理</el-menu-item>
          <el-menu-item index="/Orders"><i class="el-icon-document-remove"></i> 订单管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="/MyOrder">
        <i class="el-icon-tickets"></i>
        <span slot="title">我的订单</span>
      </el-menu-item>
      <el-menu-item index="/MyAddress">
        <i class="el-icon-edit"></i>
        <span slot="title">收货地址</span>
      </el-menu-item>
    </el-menu>
  </div>
    <div class="main">
      <div>
<router-view/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../utils/axios'
import {mapActions} from 'vuex'
import {getType} from '../service/user'

export default {
  name: 'User',
  data () {
    return {
      activeIndex: '',
      Uncreate: true
    }
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
    },
    getUserType: async function () {
      let data = await getType()
      let type = data.data.type
      if (type !== 0) {
        this.Uncreate = false
      }
    }
  },
  mounted () {
    this.initUserInfo()
  },
  created () {
    this.getUserType()
  }
}
</script>

<style scoped>
  .header{
    text-align: center;
    height: 50px;
    line-height: 49px;
    font-size: 30px;
    background-color: #2b2b2b;
    color: white;
  }
  .aside{
    float: left;
    width: 200px;
    height: 700px;
    border-right: 1px solid #e6e6e6;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
.main{
  margin:auto 200px;
  height: 700px;
  overflow: auto;
    background-color: #eae8eb;
}
</style>
