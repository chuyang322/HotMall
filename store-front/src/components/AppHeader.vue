<template>
  <div class="header w">
    <div class="top">
    <h1 class="logo">
      <router-link to="/"><img src="../assets/logo.png" title="返回主页"> </router-link>
    </h1>
    <div class="search">
        <el-input
          placeholder="请输入搜索关键词......"
          v-model="keyword"
          clearable>
        </el-input>
      <el-button type="primary" @click="search" round>搜索</el-button>
    </div>
    </div>
  </div>
</template>

<script>

import {getGoodsByKeyword} from '../service/goods'
import { mapState, mapActions } from 'vuex'
import {apiResultParse} from '../utils/apiResultParse'

export default {
  name: 'AppHeader',
  data () {
    return {
      keyword: '',
      count: '0'
    }
  },
  computed: {
    ...mapState([
      'goodsList'
    ])
  },
  methods: {
    ...mapActions([
      'goodsList/updateList',
      'goodsList/updatePages',
      'goodsList/updateTotal',
      'goodsList/updateKeyword'
    ]),
    search: async function () {
      let data = await getGoodsByKeyword(1, this.goodsList.pageSize, this.keyword)
      this['goodsList/updateKeyword']({ keyword: this.keyword })
      if (apiResultParse(data)) {
        this['goodsList/updateList']({ list: data.data.records })
        this['goodsList/updatePages']({ pages: data.data.pages })
        this['goodsList/updateTotal']({ total: data.data.total })
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .header{
    height: 150px;
    padding: 30px 0 0 0;
    background-color: #fff;
  }
  .top{
    display: flex;
  }
  .logo{
    width: 240px;
    height: 90px;
  }
  .search{
    width:500px;
    height: 50px;
    padding: 3px;
    border: 2px solid #409eff;
    border-radius: 21px;
    margin-top: 20px;
    margin-left: 50px;
  }
  .search .el-input{
    margin-left: 5px;
    width: 400px;
    border: none;
    box-sizing: border-box;
  }
  .search .el-button{
    width: 80px;
  }
  .search >>>.el-input__inner{
    border: 0;
  }
  .cart{
    width: 170px;
    height: 45px;
    margin-left: 200px;
    margin-top: 20px;
    line-height: 40px;
    border-radius: 20px;

    font-size: 15px;
    border: 1px solid #dfdfdf;
    background-color: #fde1d7;
  }
  .cart .count{
    font-size: 12px;

  }
</style>
