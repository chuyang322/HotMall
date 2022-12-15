<template>
  <el-dialog :visible.sync="showLogin"
             @close="loginClose"
             class="login"
             title=""
             width="40%">
    <img src="@/assets/logo.png"
         alt=""
         class="form-logo">
    <el-form :model="
         loginForm"
             ref="loginForm"
             :rules="rules"
             label-position="right"
             label-width="auto"
             class="form">
      <el-form-item prop="account"
                    label="账号"
                    class="form-item">
        <el-input v-model="loginForm.account"
                  placeholder="请输入账号"
                  name="account"/>
      </el-form-item>
      <el-form-item prop="password"
                    label="密码"
                    class="form-item">
        <el-input v-model="loginForm.password"
                  type="password"
                  placeholder="请输入密码"
                  name="password"
                  show-password/>
      </el-form-item>
      <el-form-item prop="signCode"
                    label="验证码"
                    class="form-item">
        <el-input v-model="loginForm.signCode"
                  placeholder="请输入验证码"
                  name="signCode"/>

      </el-form-item>
      <el-form-item class="form-item">
        <div class="sign-code">
          <el-image style="width: 80px; "
                    :src="signCodeUrl"></el-image>
          <el-button href="#"
                     type="text"
                     size="small"
                     @click="changeSignCode"
                     class="code-button"
                     primary>看不清，换一张
          </el-button>
        </div>
      </el-form-item>

      <div class="form-button">
        <el-button type="primary"
                   style=""
                   @click="submitForm()">登录
        </el-button>
        <el-button type="primary"
                   style=""
                   @click="register">注册
        </el-button>
      </div>

    </el-form>
  </el-dialog>
</template>

<script>
import {login} from '../service/user'
import {verification} from '../service/verificationCode'
import {mapActions, mapState} from 'vuex'
import {apiResultParse} from '../utils/apiResultParse'
import axios from '../utils/axios'

export default {
  name: 'v-login',
  data () {
    return {
      loginForm: {
        account: '',
        password: '',
        signCode: ''
      },
      signCodeUrl: '',
      rules: {
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '密码由8-16位字母、数字或特殊符号组成', pattern: /^[\w\s\S]{8,16}$/}
        ],
        account: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 10, max: 20, message: '长度在 10 到 20 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '账号由10-20位字母、数字或特殊符号组成', pattern: /^[\w\s\S]{10,20}$/}
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
      'showLogin',
      'user',
      'isLogin'
    ])
  },
  methods: {
    ...mapActions([
      'updateShowLogin',
      'updateShowRegister',
      'user/updateUser',
      'updateIsLogin'
    ]),
    loginClose: function () {
      this.updateShowLogin({showLogin: false})
    },
    submitForm: async function () {
      if (!this.$refs.loginForm) return
      await this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          let codeRes = await verification(this.loginForm.signCode)
          if (apiResultParse(codeRes)) {
            let data = await login({account: this.loginForm.account, password: this.loginForm.password})
            if (apiResultParse(data)) {
              this['user/updateUser'](data.data)
              this.updateIsLogin({isLogin: true})
              this.updateShowLogin({showLogin: false})
              axios.defaults.headers['token'] = data.data.token
              localStorage.setItem('token', data.data.token)
              this.$message.success('登录成功！')
            } else {
              this.$message.warning('账号或密码错误！')
            }
          }
        } else {
          this.$message.error('请按照提示完成输入')
        }
      })
    },
    changeSignCode: function () {
      this.signCodeUrl = axios.defaults.baseURL + '/verification/code?n=' + Math.random()
    },
    register: function () {
      this.updateShowLogin({showLogin: false})
      this.updateShowRegister({showRegister: true})
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
