<!--
 * @Description: 
 * @Author: chuyang
 * @Date: 2022-11-30 16:55:23
 * @LastEditors: chuyang
 * @LastEditTime: 2022-12-01 09:47:00
 * @FilePath: \undefinedc:\Users\zss10\Desktop\progran\shop.md
-->
# Model
## user 用户
- id 主键
- name 昵称
- account 账号
- password 密码
- email 邮箱
- phone 手机
- gender 性别
- age 年龄
- type 类型，普通0，开通商家1
- avatar
- last_login_time
- create_time
## address 地址
- id 主键
- name 收货人
- phone 手机
- info 详细地址
- deleted
- user_id
## shop 店铺
- id 主键
- name 店铺名称
- user_id 店主id
- info 店铺简介
- create_time 创建时间
- password 店铺二级密码
## goods 商品
- id 主键
- create_time 上架时间
- state 已上架1，下架0，仓库中（不可见）
- shop_id 店铺id
- stock 库存
- sold 已售数量
- deleted 
- price
- image
- name 
- info 简介
## cart 购物车
- id 主键
- user_id 用户id
- goods_id 商品id
- number 数量
## order 订单
- id 主键
- code 订单号
- create_time 创建时间
- pay_time 支付时间
- user_id 用户id
- price 总价
- state 未支付0，已支付1
- address_id
## order_goods 订单中的商品
- id 主键
- order_id
- goods_id
- number 数量
- state 发货中1，已收货2，退货中3，退货到4
## return_goods 退货商品
- id
- order_id
- state 申请退货1，商家同意2，不同意0，退款成功3
- number
- address_id 退货地址
- create_time
- last_update_time
## wallet 钱包
- id
- balance
- user_id
## wallet_record 消费记录
- id
- number
- type 充值0，消费1，收入3
- wallet_id
- time
## sort 类别
- id
- info
