package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hotmall.model.OrderGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {
    @Select("select goods.id as id,order_goods.id as orderId,order_goods.state as state, goods.price as price,order_goods.number as number,goods.name as name,goods.info as info,goods.image as image from goods,order_goods where order_goods.goods_id=goods.id and order_goods.order_id=${id}")
    public List<Map<String,Object>> queryOrderGoodsInfo(Integer id);

    @Select("select user_id from user_order where id = (select order_id from order_goods where id = ${id})")
    public Integer queryUserIdByOrderGoodsId(Integer id);
}
