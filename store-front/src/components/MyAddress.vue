<template>
  <div class="container">
    <v-address-editor @returnAddress="doRequest" :address-form="addressInfo" ></v-address-editor>
    <el-card>
      <h1 style="text-align: center">收货地址</h1>
    </el-card>
    <el-button type="text" icon="el-icon-edit" @click="openAddressEditor(addressInfo)">添加新地址+</el-button>
    <el-card v-for="(address,index) in addressList.list" :key="index">
      <div class="item">
        <div class="item-info">
          <span class="item-info-person">
            <span style="font-size: larger;margin-right: 20px">姓名：{{ address.name }}</span>
            <span style="font-size: small;color: #555555">电话：{{ address.phone }}</span>
          </span>
          <span class="item-info-info">地址：{{ address.info }}</span>
        </div>
        <div class="item-button">
          <el-button type="primary" @click="openAddressEditor(address)" style="margin-bottom: 10px;margin-left: 10px">修改</el-button>
          <el-button type="danger" @click="deleteAddress(address.id)">删除</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script>
import {mapActions, mapState} from 'vuex'
import {getAddress, deleteAddress, addAddress, updateAddress} from '../service/address'
import {apiResultParse} from '../utils/apiResultParse'
import AddressEditor from './AddressEditor'

export default {
  name: 'v-my-address',
  components: {
    'v-address-editor': AddressEditor
  },
  data: function () {
    return {
      addressInfo: {}
    }
  },
  computed: {
    ...mapState([
      'addressList'
    ])
  },
  methods: {
    ...mapActions([
      'addressList/updateList',
      'updateShowAddressEditor'
    ]),
    initAddressList: async function () {
      let data = await getAddress()
      if (apiResultParse(data)) {
        this['addressList/updateList']({list: data.data})
      }
    },
    openAddressEditor: function (address) {
      this.addressInfo = address
      this.updateShowAddressEditor({showAddressEditor: true})
    },
    deleteAddress: async function (id) {
      let data = await deleteAddress(id)
      if (apiResultParse(data)) {
        this.$message.success(data.message)
        await this.initAddressList()
      }
    },
    doRequest: async function (address) {
      if (address === undefined || address === {}) {
        this.$message('出错了')
        return
      }
      console.log(address.id)
      if (address.id === undefined) {
        let data = await addAddress(address)
        if (apiResultParse(data)) {
          this.$message.success(data.message)
          this.showAddressEditor = false
          await this.initAddressList()
        }
      } else {
        let data = await updateAddress(address)
        if (apiResultParse(data)) {
          this.$message.success(data.message)
          this.showAddressEditor = false
          await this.initAddressList()
        }
      }
      this.addressInfo = {}
    }
  },
  mounted () {
    this.initAddressList()
  }
}
</script>
<style scoped>
.container {
  background-color: white;
}

.item {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: center;
  align-items: center;
  margin-bottom: 3px;
}

.item-info {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
  width: 80%;
}

.item-info-person{
  display: flex;
  flex-direction: row;
  margin-bottom: 20px;
  border-bottom: 1px solid black;
}

.item-info-info{
  overflow: hidden;
}

.item-button{
  margin-left: 20px;
  overflow: hidden;
}
</style>
