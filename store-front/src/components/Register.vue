<template>
  <el-dialog :visible.sync="showRegister"
             @close="registerClose"
             title=""
             width="40%">
    <img src="@/assets/logo.png"
         alt=""
         class="form-logo"
    style="height: 100px">
    <el-form :model="registerForm"
             ref="registerForm"
             :rules="rules"
             label-position="right"
             label-width="auto"
             class="form">

      <el-form-item prop="account"
                    label="账号"
                    class="form-item">
        <el-input v-model="registerForm.account"
                  placeholder="请输入账号"
                  name="account"/>
      </el-form-item>
      <el-form-item prop="password"
                    label="密码"
                    class="form-item">
        <el-input v-model="registerForm.password"
                  type="password"
                  placeholder="请输入密码"
                  name="password"
                  show-password/>
      </el-form-item>
      <el-form-item prop="repassword"
                    label="密码"
                    class="form-item">
        <el-input v-model="registerForm.repassword"
                  type="password"
                  placeholder="请再次输入密码"
                  name="password"
                  show-password/>
      </el-form-item>
      <el-form-item prop="signCode"
                    label="验证码"
                    class="form-item">
        <el-input v-model="registerForm.signCode"
                  placeholder="请输入验证码"
                  name="signCode"/>

      </el-form-item>
      <el-form-item class="form-item">
        <div class="sign-code">
          <el-image style="width: 60px; height: 30px"
                    :src="signCodeUrl"></el-image>
          <el-button
            type="text"
            size="small"
            class="code-button"
            @click="changeSignCode"
            primary>看不清，换一张
          </el-button>
        </div>
      </el-form-item>

      <div class="form-button">
        <el-button type="primary"
                   @click="toLogin"
                   plain>登录
        </el-button>
        <el-button type="primary"
                   @click="submitForm"
                   plain>注册
        </el-button>

      </div>

    </el-form>
  </el-dialog>
</template>

<script>
import {register} from '../service/user'
import {verification} from '../service/verificationCode'
import {mapActions, mapState} from 'vuex'
import axios from 'axios'

export default {
  name: 'v-register',
  data () {
    return {
      registerForm: {
        account: '',
        password: '',
        repassword: '',
        signCode: ''
      },
      signCodeUrl: '',
      rules: {
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '密码由8-16位字母、数字或特殊符号组成', pattern: /^[\w\s\S]{8,16}$/}
        ],
        repassword: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          {min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '密码由8-16位字母、数字或特殊符号组成', pattern: /^[\w\s\S]{8,16}$/}
        ],
        account: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 10, max: 20, message: '长度在 10 到 20 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '账号由10-20位字母、汉字、数字或特殊符号组成', pattern: /^[\w\s\S]{10,20}$/}
        ],
        signCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {len: 4, message: '长度为 4 个字符', trigger: ['blur', 'change']},
          {type: 'string', pattern: /^[\w]{4}$/}
        ]
      }
    }
  },
  computed: {
    ...mapState([
      'showRegister'
    ])
  },
  methods: {
    ...mapActions([
      'updateShowRegister',
      'updateShowLogin'
    ]),
    registerClose: function () {
      this.updateShowRegister({showRegister: false})
    },
    submitForm: async function () {
      if (!this.$refs.registerForm) return
      if (this.registerForm.password !== this.registerForm.repassword) {
        this.$message.error('两次密码输入不一致')
        return
      }
      await this.$refs.registerForm.validate(async (valid) => {
        if (valid) {
          let codeRes = await verification(this.registerForm.signCode)
          if (codeRes.code === 200) {
            let data = await register({
              account: this.registerForm.account,
              password: this.registerForm.password
            })
            if (data.code === 200) {
              this.updateShowRegister({showRegister: false})
              this.$message.success(data.message)
            } else {
              this.$message.error(data.message)
            }
          } else {
            this.$message.error(codeRes.message)
          }
        } else {
          this.$message.error('请按照提示完成输入')
        }
      })
    },
    changeSignCode: function () {
      this.signCodeUrl = axios.defaults.baseURL + '/verification/code?n=' + Math.random()
      console.log(this.signCodeUrl)
    },
    toLogin: function () {
      this.updateShowRegister({showRegister: false})
      this.updateShowLogin({showLogin: true})
    }
  },
  mounted: function () {
    let _this = this
    _this.signCodeUrl = axios.defaults.baseURL + '/verification/code?n=' + Math.random()
  }

}
</script>

<style scoped>
.sign-code {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}

.form-button {
  display: flex;
  justify-content: space-around;
}

.form-logo {
  width: auto;
  height: 30%;
  aspect-ratio: 4/3;
  position: absolute;
  top: 0px;
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

.code-button {
  font-size: small;
  height: 30px;
  overflow: hidden;
  display: inline;
  margin-left: 5px;
}
</style>
