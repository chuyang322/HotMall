<template>
  <div class="container">
    <el-card>
      <h1 style="text-align: center">我的订单</h1>
    </el-card>
    <div class="main">
    <el-collapse v-model="activeIds">
      <el-collapse-item v-for="(order,index) in myOrderList.list" :key="index">
        <template slot="title" class="order-item-title">
          <div style="height: auto">
            <div>
              <span>订单号:{{ order.code }}</span>
              <span>时间:{{timeFormat(order.createTime) }}</span>
              <span>金额:<span style="color: red">￥{{ priceFormat(order.price) }}</span></span>
            </div>
            <div>
              <el-steps :space="200" :active="order.state+1" finish-status="success">
                <el-step title="下单" ></el-step>
                <el-step title="支付"></el-step>
                <el-step title="完成"></el-step>
              </el-steps>
            </div>
          </div>
        </template>
        <div v-for="(goods,index) in order.goods" :key="index" class="goods">
          <div class="goods-card">
            <img :src="goods.image" class="goods-image">
            <span style="font-size: 14px">商品:{{goods.name}}</span>
            <span>单价:<span style="color: red">￥{{ priceFormat(goods.price) }}</span></span>
            <span style="font-size: 16px">数量:X{{ goods.number }}</span>
            <span>总价:<span style="color: red">￥{{ priceFormat(goods.price*goods.number) }}</span></span>
            <span v-if="goods.state===0" style="color: red;text-align: center">商家发货中</span>
            <span v-if="goods.state===1">
              <div style="display: flex;flex-direction: column;justify-content: center">
                <span  style="color: orange;text-align: center">商品运输中</span>
                <el-button type="text" size="small" @click="confirmReceipt(goods.orderId)">确认收货</el-button>
              </div>
            </span>
            <span v-if="goods.state===2" style="color: green;text-align: center">已确认收货</span>
            <span><el-button type="danger">申请售后</el-button></span>
          </div>
        </div>
      </el-collapse-item>
    </el-collapse>
    </div>
  </div>
</template>
<script>
import {mapActions, mapState} from 'vuex'
import {priceFormat} from '../utils/priceFormat'
import {confirmReceiptGoodsById, getOrderList} from '../service/order'
import {apiResultParse} from '../utils/apiResultParse'
import {timeFormat} from '../utils/timeFormat'

export default {
  name: 'v-my-order',
  data: function () {
    return {
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
    initOrderList: async function () {
      let data = await getOrderList()
      if (apiResultParse(data)) {
        this['myOrderList/updateList']({list: data.data})
      }
    },
    confirmReceipt: async function (id) {
      let data = await confirmReceiptGoodsById(id)
      if (apiResultParse(data)) {
        this.$message.success(data.message)
        await this.initOrderList()
      }
    }
  },
  mounted () {
    this.initOrderList()
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

.goods-card span{
  margin-right: 10px;
  width: 100px;
  overflow: hidden;
}

/deep/ .el-step.is-horizontal .el-step__line{
  top: 22px;
}
span{
  margin-right: 10px;
}
</style>
