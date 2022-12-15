<template>
  <div class="ModifyInfo">
    <el-form ref="form" :model="form"  :rules="formRules" label-width="100px">
      <div class="image-container">
        <el-upload class="image-upload"
                   :show-file-list="false"
                   :on-change="handleImageChange"
                   :before-upload="beforeImageUpload"
                   action="#">
          <img v-if="form.avatar!==undefined"
               :src="(form.avatar===undefined?'#':form.avatar)"
               class="image"
               alt="点击上传"/>
        </el-upload>
      </div>
      <el-form-item label="昵称" prop="name">
        <el-input v-model="form.name" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="phone">
        <el-input v-model.number="form.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.gender" placeholder="请选择性别">
          <el-option label="男" value="1" key="1"></el-option>
          <el-option label="女" value="0" key="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input prop="age" autocomplete="off" v-model.number="form.age"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存信息</el-button>
        <el-button style="width: 90px">取消</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 200px" @click="setUp" v-if="Uncreate">创建店铺</el-button>
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="showSetUp"
               @close="setUpClose"
               class="login"
               title=""
               width="40%">
      <el-form :model="setUpForm"
               ref="setUpForm"
               :rules="rules"
               label-position="right"
               label-width="auto"
               class="form">
        <el-form-item prop="mallName"
                      label="店铺名"
                      class="form-item">
          <el-input v-model="setUpForm.mallName"
                    placeholder="请输入店铺名"
                    name="mallName"/>
        </el-form-item>
        <el-form-item prop="mallPassword"
                      label="店铺密码"
                      class="form-item">
          <el-input v-model="setUpForm.mallPassword"
                    type="password"
                    placeholder="请输入密码"
                    name="mallPassword"
                    show-password/>
        </el-form-item>
        <el-form-item prop="reMallPassword"
                      label="密码"
                      class="form-item">
          <el-input v-model="setUpForm.reMallPassword"
                    type="password"
                    placeholder="请再次输入密码"
                    name="mallPassword"
                    show-password/>
        </el-form-item>
        <el-form-item prop="mallInfo"
                      label="店铺描述"
                      class="form-item">
          <el-input v-model="setUpForm.mallInfo"
                    placeholder="请输入描述"
                    name="mallInfo"/>
        </el-form-item>
        <div class="form-button">
          <el-button type="primary"
                     style=""
                     @click="submitSetUpForm()">创建
          </el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'
import {getType, setUpShop, updateUserInfo} from '../service/user'
import {uploadImage} from '../service/image'
import {apiResultParse} from '../utils/apiResultParse'

export default {
  name: 'ModifyInfo',
  data () {
    return {
      form: {},
      Uncreate: true,
      image: undefined,
      showSetUp: false,
      formRules: {
        email: [
          {type: 'string', message: '请输入正确的邮箱格式', pattern: /^[\w\s\S]{1,64} *@{1}[0-9a-zA-Z]+\.(com|net|cn|com\.cn)$/}
        ],
        phone: [
          {pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误'}
        ],
        age: [
          {type: 'number', message: '请输入数字'}
        ]
      },
      setUpForm: {
        mallName: '',
        mallInfo: '',
        mallPassword: '',
        reMallPassword: ''
      },
      rules: {
        mallPassword: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '密码由8-16位字母、数字或特殊符号组成', pattern: /^[\w\s\S]{8,16}$/}
        ],
        reMallPassword: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          {min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: ['blur', 'change']},
          {type: 'string', message: '密码由8-16位字母、数字或特殊符号组成', pattern: /^[\w\s\S]{8,16}$/}
        ]
      }
    }
  },
  computed: {
    ...mapState([
      'user'
    ])
  },
  methods: {
    ...mapActions([
      'user/updateAvatar',
      'user/updateUser',
      'user/updateType'
    ]),
    handleUploadImage: async function () {
      if (this.image === undefined) return
      let fd = new FormData()
      fd.append('image', this.image.raw)
      let data = await uploadImage(fd)
      if (apiResultParse(data)) {
        this['user/updateAvatar']({avatar: data.data})
        this.image = undefined
        return true
      } else {
        this.image = undefined
        return false
      }
    },
    handleSubmitForm: async function () {
      let data = await updateUserInfo(this.form)
      if (apiResultParse(data)) {
        console.log('o', this.user)
        let u = this.user
        console.log(u)
        u.image = this.form.image
        u.name = this.form.name
        u.email = this.form.email
        u.phone = this.form.phone
        u.gender = this.form.gender
        u.age = this.form.age
        this['user/updateUser']({ user: u })
        console.log('n', this.user)
      }
    },
    onSubmit () {
      this.$refs.form.validate(flag => {
        if (flag === false) return
        this.$confirm('是否确认修改？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return new Promise((resolve) => {
            resolve(this.handleUploadImage())
          }).then(() => {
            this.handleSubmitForm()
          }).then(() => {
            this.$message.success('修改成功')
          })
        })
      })
    },
    setUp: async function () {
      this.showSetUp = true
    },
    setUpClose: function () {
      this.showSetUp = false
    },
    submitSetUpForm: async function () {
      if (!this.$refs.setUpForm) return
      if (this.setUpForm.mallPassword !== this.setUpForm.reMallPassword) {
        this.$message.error('两次密码输入不一致')
        return
      }
      await this.$refs.setUpForm.validate(async (valid) => {
        if (valid) {
          let data = await setUpShop({
            name: this.setUpForm.mallName,
            password: this.setUpForm.mallPassword,
            info: this.setUpForm.mallInfo
          })
          if (data.code === 200) {
            this.setUpClose()
            this.$message.success(data.message)
          } else {
            this.$message.error(data.message)
          }
        } else {
          this.$message.error('请按照提示完成输入')
        }
      })
      location.reload()
    },
    handleImageChange (file) {
      this.image = file
      this.form.avatar = URL.createObjectURL(file.raw)
    },
    beforeImageUpload (rawFile) {
      if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
        this.$message.error('图片必须为jpg或png')
        return false
      } else if (rawFile.size / 1024 / 1024 > 2) {
        this.$message.error('图片大小不超过2MB')
        return false
      }
      return true
    },
    getUserType: async function () {
      let data = await getType()
      let type = data.data.type
      if (type !== 0) {
        this.Uncreate = false
      }
    }
  },
  mounted () {
    this.form = this.user
  },
  created () {
    this.getUserType()
  }
}
</script>

<style scoped>
.ModifyInfo {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

.image-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.image-upload {
  height: 160px;
  width: 120px;
}

.image {
  height: 200px;
  width: 120px;
}

.image-upload {
  border: 1px dashed black;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
</style>
