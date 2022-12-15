<template>
  <div class="goods-list">
    <v-address-card @selectAddress="getAddressId"></v-address-card>
    <div class="goods-container">
      <div v-for="(goods,index) in goodsList.list"
           :key="index"
           class="goods-card"
           @click="goodsInfo(goods)">
        <div>
          <el-image class="goods-image"
                    :src="goods.image">
          </el-image>
          <div class="goods-info">
            <div class="goods-name">{{ goods.name }}</div>
            <div class="goods-price">￥{{ priceFormat(goods.price) }}</div>
          </div>
        </div>
      </div>
      <el-dialog
        title="详情>"
        :visible.sync="dialogVisible"
        width="700px">
        <div class="goods-card-container">
          <div style="width: 100%;float: left;margin-left: 20px">
            <span v-if="address.id!==undefined" style="margin-left: 0;overflow: hidden" @click="openAddressCard" >
              <span style="font-weight: bold">收货地址:&nbsp;</span>{{address.name}}&nbsp;&nbsp;{{address.phone}}&nbsp;&nbsp;{{ address.info }}
            </span>
            <el-button v-else @click="openAddressCard" type="text">请选择收货地址</el-button>
          </div>
          <div style="flex-direction: row">
            <div class="img">
              <el-image class="goods-image"
                        :src="tempGoods.image"
                        style="height: 300px">
              </el-image>
            </div>
            <div class="goods_detail_list">
              <h3>{{ tempGoods.name }}</h3>
              <p>{{ tempGoods.info }}</p>
              <div class="prize_bar">
                <div class="show_prize">￥<em>{{ priceFormat(tempGoods.price) }}</em></div>
              </div>
              <div class="goods_num goods-number">
                <div class="">数量：</div>
                <el-input-number v-model="number"
                                 :min="1"
                                 :max="10"/>
              </div>
              <div class="total">总价：<em>¥{{ priceFormat(tempGoods.price * number) }}</em></div>
            </div>
            <span slot="footer" class="dialog-footer" style="float: right">
              <el-button @click="addToCart(tempGoods.id,number)">加入购物车</el-button>
              <el-button type="primary" @click="buyIt(tempGoods.id,number,address.id)">直接购买</el-button>
            </span>
          </div>
        </div>
      </el-dialog>
    </div>
    <div class="page">
      <el-pagination background
                     layout="prev, pager, next"
                     :total="goodsList.total"
                     :page-size="goodsList.pageSize"
                     :current-page="current"
                     @current-change="handleCurrentChange"/>
    </div>
  </div>
</template>

<script>
import {getGoodsList} from '../service/goods'
import {mapActions, mapState} from 'vuex'
import {priceFormat} from '../utils/priceFormat'
import {apiResultParse} from '../utils/apiResultParse'
import {addGoods} from '../service/cart'
import AddressCard from './AddressCard'
import {createOrder} from '../service/order'

export default {
  name: 'GoodsList',
  components: {
    'v-address-card': AddressCard
  },
  data () {
    return {
      current: 1,
      priceFormat: priceFormat,
      dialogVisible: false,
      tempGoods: {},
      number: 0,
      address: {}
    }
  },
  computed: {
    ...mapState([
      'goodsList'
    ])
  },
  methods: {
    goodsInfo (goods) {
      this.tempGoods = goods
      this.dialogVisible = true
    },
    ...mapActions([
      'goodsList/updateList',
      'goodsList/updatePages',
      'goodsList/updateTotal',
      'goodsList/updateCurrent',
      'goods/updateGoods',
      'updateShowAddressCard'
    ]),
    getGoods: async function () {
      let data = await getGoodsList(1, this.goodsList.pageSize)
      if (apiResultParse(data)) {
        this['goodsList/updateList']({list: data.data.records})
        this['goodsList/updatePages']({pages: data.data.pages})
        this['goodsList/updateTotal']({total: data.data.total})
      }
    },
    handleCurrentChange: async function (current) {
      this.current = current
      let data = await getGoodsList(current, this.goodsList.pageSize)
      if (data) {
        this['goodsList/updateList']({list: data.data.records})
        this['goodsList/updatePages']({pages: data.data.pages})
        this['goodsList/updateTotal']({total: data.data.total})
        this['goodsList/updateCurrent']({current: data.data.current})
      }
    },
    addToCart: async function (id, number) {
      let data = await addGoods(id, number)
      if (apiResultParse(data)) {
        this.$message.success('成功加入购物车')
      }
    },
    buyIt: async function (goodsId, number, addressId) {
      if (this.address.id === undefined) {
        this.$message.warning('请选择收货地址')
        return
      }
      let list = [{id: goodsId, number: number}]
      let data = await createOrder(list, addressId)
      if (apiResultParse(data)) {
        this.$message.success(data.message)
      }
      this.dialogVisible = false
    },
    getAddressId: function (address) {
      this.address = address
    },
    openAddressCard: function () {
      this.updateShowAddressCard({showAddressCard: true})
    }
  },
  mounted () {
    this.getGoods()
  }
}
</script>

<style scoped>
.goods-container {
  display: flex;
  flex-direction: row;
  flex-flow: wrap;
  margin: 10px 10px 10px 10px;
  height: 100%;
}

.goods-card {
  border-radius: 6px;
  margin: 10px auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: rgb(202, 202, 202) 3px 3px;
}

.goods-card:hover {
  box-shadow: 4px 8px 12px #555555;
  transition: box-shadow 0.2s;
}

.goods-image {
  margin: 10px 20px;
  height: 150px;
  width: auto;
  border-radius: 6px;
}

.goods-name {
  width: 180px;
  font-size: 16px;
  white-space: nowrap;
  overflow: hidden;
  margin-left: 10px;
}

.goods-price {
  text-align: right;
  color: red;
  margin-right: 10px;
  font-size: 14px;
}

.page {
  text-align: center;
  margin-top: 50px;
}

.img {
  float: left;
}

.goods_detail_list {
  width: 300px;
  height: 350px;
  margin-left: 350px;
}

.goods_detail_list h3 {
  font-size: 24px;
  line-height: 30px;
  color: #666;
  font-weight: normal;
}

.goods_detail_list p {
  color: #666;
  line-height: 40px;
}

.prize_bar {
  height: 72px;
  background-color: #fff5f5;
  line-height: 72px;
}

.prize_bar .show_prize {
  font-size: 20px;
  color: #ff3e3e;
  padding-left: 20px;
}

.prize_bar .show_pirze em {
  font-style: normal;
  font-size: 36px;
  padding-left: 10px;
}

.prize_bar .show_unit {
  padding-left: 150px;
}

.goods_num {
  height: 52px;
  margin-top: 19px;
  /*background: yellow;*/
}

.goods_num .num_name {
  width: 70px;
  height: 52px;
  line-height: 52px;
}

.goods_num .num_add {
  width: 75px;
  height: 50px;
  border: 1px solid #dddddd;
}

.goods_num .num_add .num_show {
  width: 49px;
  height: 50px;
  text-align: center;
  line-height: 50px;
  border: 0px;
  outline: none;
  font-size: 14px;
  color: #666;
}

.goods_num .num_add .add,
.goods_num .num_add .minus {
  width: 25px;
  line-height: 25px;
  text-align: center;
  border-left: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  color: #666;
  font-size: 14px;
}

.goods_num .num_add .minus {
  border-bottom: 0px;
}

.total {
  height: 35px;
  line-height: 35px;
  margin-top: 25px;
  /*background: yellow;*/
}

.total em {
  font-style: normal;
  color: #ff3e3e;
  font-size: 18px;
}

.goods-card-container {
  display: flex;
  flex-direction: column;
}

</style>
