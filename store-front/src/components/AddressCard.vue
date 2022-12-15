<template>
  <el-dialog :visible.sync="showAddressCard"
             @close="cardClose"
             class="address-card"
             title="请选择收货地址"
             width="40%"
             @open="open">
    <div v-for="(address,index) in addressList.list" :key="index">
      <div @click="selectAddress(address)" class="item">
        <div class="item-info">
          <span>收货人：{{ address.name }}</span>
          <span>电话：{{ address.phone }}</span>
          <span>地址：{{ address.info }}</span>
        </div>
      </div>
    </div>
    <router-link to="/MyAddress"><el-button type="text" icon="el-icon-edit">添加地址</el-button></router-link>
  </el-dialog>
</template>
<script>
import {mapActions, mapState} from 'vuex'
import {getAddress} from '../service/address'
import {apiResultParse} from '../utils/apiResultParse'

export default {
  name: 'v-address-card',
  data: function () {
    return {}
  },
  computed: {
    ...mapState([
      'showAddressCard',
      'addressList'
    ])
  },
  methods: {
    ...mapActions([
      'addressList/updateList',
      'updateShowAddressCard'
    ]),
    cardClose: function () {
      this.updateShowAddressCard({showAddressCard: false})
    },
    selectAddress: function (address) {
      this.$emit('selectAddress', address)
      this.cardClose()
    },
    initAddressList: async function () {
      let data = await getAddress()
      if (apiResultParse(data)) {
        this['addressList/updateList']({list: data.data})
      }
    },
    open: function () {
      this.initAddressList()
    }
  }
}
</script>
<style scoped>
.address-card {
  z-index: 1000;
  margin: auto;
}

.item {
  display: flex;
  flex-direction: row;
}

.item-info {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}
</style>
