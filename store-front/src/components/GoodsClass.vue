<template>
  <div class="hello">
    <div class="left">
      <ul>
        <li @click="allGoods">全部商品<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('服装')">服装<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('鞋子')">鞋子<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('运动')">运动<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('手机')">手机/电脑<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('家居')">家居/家具<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('食物')">食物/生鲜<i class="el-icon-arrow-right"></i> </li>
        <li @click="sort('书籍')">图书/书籍<i class="el-icon-arrow-right"></i> </li>
      </ul>
    </div>
    <div class="right">
      <el-carousel>
        <el-carousel-item v-for="item in list" :key="item.name">
          <img :src="item.src">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>

import {mapActions, mapState} from 'vuex'
import {apiResultParse} from '../utils/apiResultParse'
import {getGoodsList, getSortList} from '../service/goods'

export default {

  name: 'GoodsClass',
  data () {
    return {
      keyword: '',
      list: [
        {
          name: 'img1',
          src: require('../assets/img1.jpg')
        },
        {
          name: 'img2',
          src: require('../assets/img2.jpg')
        },
        {
          name: 'img3',
          src: require('../assets/img3.jpg')
        },
        {
          name: 'img4',
          src: require('../assets/img4.jpg')
        }
      ]
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
    allGoods: async function () {
      let data = await getGoodsList(1, this.goodsList.pageSize)
      if (apiResultParse(data)) {
        this['goodsList/updateList']({ list: data.data.records })
        this['goodsList/updatePages']({ pages: data.data.pages })
        this['goodsList/updateTotal']({ total: data.data.total })
      }
    },
    sort: async function (info) {
      let data = await getSortList(1, this.goodsList.pageSize, info)
      this['goodsList/updateKeyword']({ keyword: info })
      if (apiResultParse(data)) {
        this['goodsList/updateList']({ list: data.data.records })
        this['goodsList/updatePages']({ pages: data.data.pages })
        this['goodsList/updateTotal']({ total: data.data.total })
      }
    }
  }
}
</script>

<style scoped>
  .hello{
    height: 400px;
    margin-top: 30px;
    border: 1px solid #e9e9e9;
    border-radius: 5px;
    background-color: #f9f9f9;
  }
  ul{
    float: left;
    margin:35px 50px;
    width: 200px;
    border-radius: 5px;
    background-color:#f65835 ;
  }
  ul li{
    margin: 10px 10px;
    height: 31px;
    line-height: 30px;
    font-size: 16px;
    color: white;
    border-bottom: 1px solid #ffffff;
  }
  ul li:hover{

    background-color: #f9947c;
  }
  .el-icon-arrow-right{
    float: right;
    margin-top: 8px;
  }
  .right{
    margin-left: 400px;
  }
  .el-carousel{
    margin-top: 60px;
    height: 300px;
    width: 520px;
  }
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
