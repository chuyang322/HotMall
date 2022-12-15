<template>
  <div class="container">
    <v-address-card @selectAddress="getAddressId"></v-address-card>
    <div class="header">
      <div style="display: flex;flex-direction: column">
        <div style="display: flex;flex-direction: row;">
          <div class="left" style="float: left">
            <h1>购物车（{{ carList.CarTotal }}）</h1>
          </div>
          <div class="right" style="margin-left: 510px;">
            <span class="carAmount" >￥{{ cost }}</span>
            <el-button style="background-color: #f65835;color: white;" @click="buyThese">购&nbsp;买</el-button>
          </div>
        </div>
        <div style="width: 100%;float: left;margin-left: 20px">
          <span v-if="address.id!==undefined" style="margin-left: 0;overflow: hidden" @click="openAddressCard">
              <span style="font-weight: bold">收货地址:&nbsp;</span>{{ address.name }}&nbsp;&nbsp;{{ address.phone }}&nbsp;&nbsp;{{ address.info }}
          </span>
          <el-button v-else @click="openAddressCard" type="text">请选择收货地址</el-button>
        </div>
      </div>
      <hr>
      <ul class="top">
        <li style="width: 10px;margin-left: 20px">
          <input type="checkbox" id="all" @click="checkAll" :checked="allCheck"/>
        </li>
        <li style="margin:auto 50px">图片</li>
        <li style="margin-left: 100px">商品信息</li>
        <li style="margin-left: 130px">单价</li>
        <li>数量</li>
        <li>金额</li>
        <li style="margin-left: 70px">删除</li>
      </ul>
    </div>
    <div class="carGoodsList">
      <div v-for="(carGoods,index) in carList.CarList"
           :key="index"
           class="goods-card">
        <div class="check">
          <input type="checkbox"
                 v-model="checkedData"
                 name="check"
                 :value="{id:carGoods.goodsId,money:carGoods.number*carGoods.price,number:carGoods.number}"/>
        </div>
        <div class="card">
          <div class="check"></div>
          <div class="goods-image ">
            <el-image style="height: 100px;"
                      :src="carGoods.image">
            </el-image>
          </div>
          <div class="info">
            <div class="goods-name">{{ carGoods.name }}</div>
            <div class="goods-info">{{ carGoods.info }}</div>
            <div class="goods-sold">已售：{{ carGoods.sold }}</div>
          </div>
          <div class="goods-price">￥{{ priceFormat(carGoods.price) }}</div>
          <div class="number">
            <el-input-number v-model="carGoods.number"
                             :min="1"
                             :max="carGoods.stock"
                             size="small"
                             @change="handleInputChange(carGoods.goodsId,carGoods.number)"/>
            <div class="goods-stock">库存：{{ carGoods.stock }}</div>
          </div>
          <div class="amount">¥{{ priceFormat(carGoods.price * carGoods.number) }}</div>
          <div class="delete">
            <el-button @click="deleteEvent(carGoods.goodsId)" type="danger" round>删除</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'
import {apiResultParse} from '../utils/apiResultParse'
import {priceFormat} from '../utils/priceFormat'
import {deleteById, getGoodsByUserId, updateNumber} from '../service/cart'
import AddressCard from './AddressCard'
import {createOrder} from '../service/order'

export default {
  name: 'CarGoods',
  components: {
    'v-address-card': AddressCard
  },
  data () {
    return {
      current: 1,
      priceFormat: priceFormat,
      checkedData: [],
      address: {},
      dialogVisible: false
    }
  },
  computed: {
    ...mapState([
      'carList',
      'carList/CarTotal'
    ]),
    cost: function () {
      let sum = 0
      if (this.checkedData.length === 0) return sum
      for (let data in this.checkedData) {
        sum += this.checkedData[data].money
      }
      return priceFormat(sum)
    },
    ids: function () {
      let list = []
      for (let data in this.checkedData) {
        list.push(this.checkedData[data].id)
      }
      return list
    },
    allCheck: function () {
      if (this.checkedData.length !== document.getElementsByName('check').length || this.checkedData.length === 0) {
        return false
      }
      return true
    },
    list: function () {
      let list = []
      for (let data in this.checkedData) {
        list.push({id: this.checkedData[data].id, number: this.checkedData[data].number})
      }
      return list
    }
  },
  methods: {
    ...mapActions([
      'carList/updateCarList',
      'carList/updateCarTotal',
      'updateShowAddressCard'
    ]),
    getCarGoods: async function () {
      let data = await getGoodsByUserId()
      if (apiResultParse(data)) {
        this['carList/updateCarList']({CarList: data.data})
        this['carList/updateCarTotal']({CarTotal: data.data.length})
      }
    },
    deleteEvent: async function (id) {
      let data = await deleteById(id)
      if (apiResultParse(data)) {
        this.$message.success(data.message)
      }
      await this.getCarGoods()
    },
    checkAll: function () {
      let flag = this.allCheck
      let checks = document.getElementsByName('check')
      for (let i = 0; i < checks.length; i++) {
        if (checks[i].checked === flag) {
          checks[i].click()
        }
      }
    },
    handleInputChange: function (id, number) {
      updateNumber(id, number)
    },
    buyThese: async function () {
      if (this.address.id === undefined) {
        this.$message.warning('请选择收货地址')
      }
      let data = await createOrder(this.list, this.address.id)
      if (apiResultParse(data)) {
        this.$message.success(data.message)
      }
    },
    getAddressId: function (address) {
      this.address = address
    },
    openAddressCard: function () {
      this.updateShowAddressCard({showAddressCard: true})
    }
  },
  mounted () {
    this.getCarGoods()
    console.log(this.carList)
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
}

.header {
  margin-top: 20px;
  height: 150px;
  line-height: 50px;
  padding-left: 10px;
  border-bottom: 1px solid #e6e6e6;
}

.header .right {
  margin-right: 20px;
}

.carAmount {
  display: inline-block;
  width: 100px;
  color: #ff3e3e;
  font-size: 18px;
}

ul li {
  float: left;
}

ul li:nth-child(n+4) {
  margin-left: 100px;
}

.goods-card {
  height: 150px;
  margin: 10px 20px;
  line-height: 149px;
  border-radius: 10px;
  background-color: #eae8eb;
  padding: 20px 5px;
}

.card {
  display: flex;
  height: 100px;
  line-height: 100px;
}

.check {
  float: left;
  height: 100px;
  line-height: 100px;
  margin-right: 5px;
}

.goods-image {
  width: 130px;
}

.info {
  width: 200px;
  margin-left: 30px;
}

.info:nth-child(2) {
  width: 200px;
  margin-left: 20px;
}

.goods-name {
  height: 30px;
  line-height: 20px;
  font-size: 18px;
  color: #2b2b2b;
}

.goods-info {
  height: 40px;
  line-height: 20px;
  overflow: hidden;
}

.goods-sold {
  height: 30px;
  line-height: 30px;
  color: #f65835;
}

.goods-price {
  width: 50px;
  margin-left: 20px;
  font-size: 16px;
}

.number {
  margin-left: 40px;
  height: 60px;
  line-height: 60px;
}

.goods-stock {
  text-align: center;
}

.amount {
  width: 100px;
  color: #ff3e3e;
  font-size: 18px;
  margin-left: 20px;
}

.goods-card-container {
  display: flex;
  flex-direction: column;
}

.top{
  display: flex;
  flex-direction: row;
}

</style>
