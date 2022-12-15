<template>
  <el-dialog :visible.sync="showAddressEditor"
             class="editor"
             title=""
             width="40%"
             @close="editorClose"
             @open="open">
    <el-form :model="address"
             ref="address"
             :rules="rules"
             label-position="left"
             label-width="auto"
             class="form">
      <el-form-item prop="name"
                    label="收货人"
                    class="form-item">
        <el-input v-model="address.name"
                  placeholder="请输入收货人姓名"
                  name="name"/>
      </el-form-item>
      <el-form-item prop="phone"
                    label="手机号"
                    class="form-item">
        <el-input v-model="address.phone"
                  placeholder="请输入手机号"
                  name="phone"/>
      </el-form-item>
      <el-form-item prop="info"
                    label="地址"
                    class="form-item">
        <el-input v-model="address.info"
                  placeholder="请输入地址"
                  name="info"/>
      </el-form-item>
      <div class="form-button">
        <el-button type="primary"
                   style=""
                   @click="submitForm()">提交
        </el-button>
      </div>
    </el-form>
  </el-dialog>
</template>

<script>

import {mapActions, mapState} from 'vuex'

export default {
  name: 'v-address-editor',
  props: [
    'addressForm'
  ],
  data () {
    return {
      address: {},
      rules: {
        name: [
          {required: true, message: '请输入收货姓名', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误'}
        ],
        info: [
          {required: true, message: '请输入地址', trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    ...mapState([
      'showAddressEditor'
    ])
  },
  methods: {
    ...mapActions([
      'updateShowAddressEditor'
    ]),
    submitForm: function () {
      if (!this.$refs.address) return
      this.$refs.address.validate((valid) => {
        if (valid) {
          this.$emit('returnAddress', this.address)
          this.editorClose()
        } else {
          this.$message.error('请按照提示完成输入')
        }
      })
    },
    editorClose: function () {
      this.updateShowAddressEditor({showAddressEditor: false})
    },
    open: function () {
      this.address = this.addressForm
      this.$forceUpdate()
    }
  }
}
</script>

<style scoped>
.form-button {
  display: flex;
  justify-content: space-around;
}

.form {
  margin-top: 10%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-item {
  height: 20%;
  width: 80%;
}
</style>
