<template>
  <div class="container">
    <el-card>
      <h1 style="text-align: center">店铺订单</h1>
    </el-card>
    <div class="main">
      <el-collapse v-model="activeIds">
        <el-collapse-item v-for="(order,index) in myOrders" :key="index">
          <template slot="title" class="order-item-title">
            <div style="height: auto">
              <div>
                <span>订单号:{{ order.code }}</span>
                <span>时间:{{timeFormat(order.createTime) }}</span>
                <span>金额:<span style="color: red">￥{{ priceFormat(order.price) }}</span></span>
              </div>
              <div>
                <el-steps :space="200" :active="order.state" finish-status="success">
                  <el-step title="已支付"></el-step>
                  <el-step title="已发货"></el-step>
                  <el-step title="已完成"></el-step>
                </el-steps>
                <el-button :disabled="order.state!=1" style="margin-top: 12px;" type="primary" @click="next(order.id)">确认发货</el-button>
              </div>
            </div>
          </template>
          <div v-for="(goods,index) in order.goods" :key="index" class="goods">
            <!--<div v-for="address in order.address" :key="address.id" class="address">-->
            <div class="goods-card">
              <img :src="goods.image" class="goods-image">
              <!--<span>地址：{{order.address.info}}</span>-->
              <span style="font-size: 14px">商品：{{goods.name}}</span>
              <span style="font-size: 16px">数量:X{{ goods.number }}</span>
              <span>价格:<span style="color: red">￥{{ priceFormat(goods.price) }}</span></span>
            </div>
            <!--</div>-->
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>
<script>
import {mapActions, mapState} from 'vuex'
import {priceFormat} from '../utils/priceFormat'
import {getMyOrders, updateState} from '../service/order'
import {apiResultParse} from '../utils/apiResultParse'
import {timeFormat} from '../utils/timeFormat'

export default {
  name: 'orders',
  data: function () {
    return {
      myOrders: [],
      timeFormat: timeFormat,
      activeIds: [],
      priceFormat: priceFormat
    }
  },
  computed: {
    ...mapState([
      'myOrderList'
    ])
  },
  methods: {
    ...mapActions([
      'myOrderList/updateList'
    ]),
    initMyOrders: async function () {
      let data = await getMyOrders()
      if (apiResultParse(data)) {
        this.myOrders = data.data
      }
    },
    next: async function (id) {
      await updateState(id)
      this.initMyOrders()
    }
  },
  mounted () {
    this.initMyOrders()
  }
}
</script>
<style scoped>
  .container {
    margin: auto 100px;
    width: 900px;
    border-radius: 10px;
    background-color: #ffffff;
    padding-bottom: 20px;
    /*background-color: white;*/
  }
  .main{
    margin-left: 50px;
  }
  /deep/ .el-collapse-item__header {
    height: auto !important;
    min-height: 100px !important;
  }

  .goods{
    height: 100px;
  }

  .goods-card{
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center!important;
  }

  /deep/ .el-collapse-item__content{
    padding-bottom: 0 !important;
    border-top: 1px solid #828282;
  }

  .goods-image{
    height: 90%;
    margin-left: 10px;
  }

  span{
    margin-right: 10px;
  }

  /deep/ .el-step.is-horizontal .el-step__line{
    top: 22px;
  }
</style>
