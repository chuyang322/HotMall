<template>
  <div>
    <el-card class="crumbs-card">
      <h1 style="text-align: center">{{mallName}}</h1>
    </el-card>
    <el-card class="container">
      <el-form :inline="true"  class="demo-form-inline">
        <el-form-item label="商品名">
          <el-input v-model="keyword" clearable placeholder="请输入关键词......"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="selectGoods">查询</el-button>
          <el-button type="danger" v-if="ids.length>0" @click="deletesEvent">下架选中</el-button>
          <el-button type="primary" @click="showDialog(undefined)">添加</el-button>
        </el-form-item>
      </el-form>
      <el-table
        ref="multipleTable"
        tooltip-effect="dark"
        :data="myGoodsList.list"
        @selection-change="selectChange"
        style="width: 100%;"
        :row-style="{height: '150px'}">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="image"
          label="图片">
          <template slot-scope="scope">
            <img width="100px" :src="scope.row.image" alt="">
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="商品名"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column
          prop="info"
          label="商品信息"
          show-overflow-tooltip
        >
        </el-table-column>
          <el-table-column
          label="类型">
            <template slot-scope="scope">
              <span>{{scope.row.sortInfo.info}}</span>
            </template>
          </el-table-column>
        <el-table-column
          label="价格">
          <template slot-scope="scope">
            <span style="color: red">￥{{priceFormat(scope.row.price)}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="stock"
          label="库存">
        </el-table-column>
        <el-table-column
          prop="sold"
          label="已售">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="showDialog(scope.row)">更新</el-button>
            <el-button type="text" @click="deleteEvent(scope.row)">下架</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page">
        <el-pagination background
                       layout="prev, pager, next"
                       :total="myGoodsList.total"
                       :page-size="myGoodsList.pageSize"
                       :current-page="current"
                       @current-change="handleCurrentChange" />
      </div>
    </el-card>
    <el-dialog
      title="修改/添加"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="30%"
      @close="closeDialog">
      <el-form label-width="80px" :model="tempGoods" ref="form" :rules="rules">
        <el-form-item label="商品图片">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :on-change="handleImageChange"
            :before-upload="beforeImageUpload">
            <img v-if="tempGoods.image" :src="tempGoods.image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品名" prop="name">
          <el-input v-model="tempGoods.name"></el-input>
        </el-form-item>
        <el-form-item label="商品信息" prop="info">
          <el-input v-model="tempGoods.info"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="sortId">
          <el-select v-model="tempGoods.sortId" placeholder="请选择商品类型">
            <el-option v-for="allSort in sorts" :key="allSort.id" :label="allSort.info" :value="allSort.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price" >
          <el-input v-model="tempGoods.price" @input="toNum"></el-input>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model.number="tempGoods.stock"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" @click="submit">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {mapActions, mapState} from 'vuex'
import {priceFormat} from '../utils/priceFormat'
import {
  deleteById,
  deleteByIds,
  getMyGoodsByKeyword,
  getMyGoodsList,
  getMyShop,
  saveGoods,
  updateGoods
} from '../service/shop'
import {apiResultParse} from '../utils/apiResultParse'
import {allSort} from '../service/sort'
import {uploadImage} from '../service/image'

export default {
  name: 'MyMall',
  data: function () {
    return {
      image: undefined,
      current: 1,
      priceFormat: priceFormat,
      keyword: '',
      myMall: {},
      mallName: '',
      ids: [],
      dialogVisible: false,
      tempGoods: {},
      imageUrl: '',
      sorts: [],
      rules: {
        name: [
          { required: true, message: '请输入商品名', trigger: 'blur' }
        ],
        price: [
          {required: true, message: '不能为空'}
        ],
        stock: [
          {required: true, message: '不能为空'},
          {type: 'number', message: '必须为数字值'}
        ],
        sortId: [
          {required: true, message: '请选择类型'}
        ],
        info: [
          {required: true, message: '不能为空'}
        ]
      }
    }
  },
  computed: {
    ...mapState([
      'myGoodsList'
    ])
  },
  methods: {
    ...mapActions([
      'myGoodsList/updateList',
      'myGoodsList/updatePages',
      'myGoodsList/updateTotal',
      'myGoodsList/updateCurrent',
      'myGoodsList/updateKeyword'
    ]),
    selectChange (arr) {
      this.ids = arr.map(x => x.id)
    },
    getMyGoods: async function () {
      let data = await getMyGoodsList(1, this.myGoodsList.pageSize)
      if (apiResultParse(data)) {
        this['myGoodsList/updateList']({ list: data.data.records })
        this['myGoodsList/updatePages']({ pages: data.data.pages })
        this['myGoodsList/updateTotal']({ total: data.data.total })
      }
    },
    handleCurrentChange: async function (current) {
      this.current = current
      let data = await getMyGoodsList(current, this.myGoodsList.pageSize)
      if (apiResultParse(data)) {
        this['myGoodsList/updateList']({ list: data.data.records })
        this['myGoodsList/updatePages']({ pages: data.data.pages })
        this['myGoodsList/updateTotal']({ total: data.data.total })
        this['myGoodsList/updateCurrent']({ current: data.data.current })
      }
    },
    selectGoods: async function () {
      let data = await getMyGoodsByKeyword(1, this.myGoodsList.pageSize, this.keyword)
      this['myGoodsList/updateKeyword']({keyword: this.keyword})
      if (apiResultParse(data)) {
        this['myGoodsList/updateList']({ list: data.data.records })
        this['myGoodsList/updatePages']({ pages: data.data.pages })
        this['myGoodsList/updateTotal']({ total: data.data.total })
      }
    },
    getMyMall: async function () {
      this.myMall = await getMyShop()
      this.mallName = this.myMall.data.name
    },
    getAllSort: async function () {
      let data = await allSort(1, 10)
      if (apiResultParse(data)) {
        this.sorts = data.data.records
      }
    },
    showDialog (row) {
      if (row !== undefined) {
        this.tempGoods = JSON.parse(JSON.stringify(row))
        this.tempGoods.price = priceFormat(this.tempGoods.price)
      }
      this.dialogVisible = true
    },
    submit () {
      this.$refs.form.validate(flag => {
        if (flag === false) return
        this.$confirm('是否保存修改?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          new Promise((resolve) => {
            resolve(this.handleUploadImage())
          }).then(() => {
            if (this.handleSubmitForm()) {
              this.$message.success('提交成功')
            } else {
              this.$message.error('提交失败')
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消修改'
          })
        })
        this.getMyGoods()
      })
    },
    handleSubmitForm: async function () {
      this.tempGoods.price = parseInt(this.fixPrice(this.tempGoods.price))
      console.log(this.tempGoods.price)
      let data
      if (this.tempGoods.id === undefined) {
        console.log(this.tempGoods.sortId)
        console.log(this.tempGoods)
        data = await saveGoods(this.tempGoods)
      } else {
        data = await updateGoods(this.tempGoods)
      }
      return apiResultParse(data)
    },
    handleClose () {
      this.getMyGoods()
      this.dialogVisible = false
    },
    deleteEvent: async function (row) {
      this.$confirm('此操作将下架该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await deleteById(row.id)
        this.$message({
          type: 'success',
          message: '成功下架'
        })
        this.getMyGoods()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消下架'
        })
      })
    },
    deletesEvent () {
      this.$confirm('是否下架这' + this.ids.length + '件商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          ids: this.ids
        }
        deleteByIds(obj)
        this.$message.success('下架成功')
        this.getMyGoods()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消下架'
        })
      })
    },
    handleImageChange (file) {
      this.image = file
      this.tempGoods.image = URL.createObjectURL(file.raw)
      this.$forceUpdate()
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
    handleUploadImage: async function () {
      if (this.image === undefined) return
      let fd = new FormData()
      fd.append('image', this.image.raw)
      let data = await uploadImage(fd)
      if (apiResultParse(data)) {
        this.tempGoods.image = data.data
        this.image = undefined
        return true
      } else {
        this.image = undefined
        return false
      }
    },
    closeDialog: function () {
      this.dialogVisible = false
      this.tempGoods = {}
    },
    toNum: function () {
      this.tempGoods.price = this.tempGoods.price.replace(/[^\d.]/g, '')
      this.tempGoods.price = this.tempGoods.price.replace(/^\./g, '')
      this.tempGoods.price = this.tempGoods.price.replace(/\.{2,}/g, '.')
      this.tempGoods.price = this.tempGoods.price.replace(/^(-)*(\d+)\.(\d\d).*$/, '$1$2.$3')
    },
    fixPrice: function (str) {
      let flag = str.length - str.indexOf('.')
      if (flag === 3) {
        str = str.replace('.', '')
      } else if (flag === 2) {
        str = str.replace('.', '') + '0'
        console.log(str)
      } else if (flag === 1) {
        str = str.replace('.', '') + '00'
      } else {
        str += '00'
      }
      console.log(str)
      return str
    }
  },
  created () {
    this.getMyGoods()
    this.getMyMall()
    this.getAllSort()
  }
}
</script>

<style scoped>
  .page {
    text-align: center;
    margin-top: 50px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
