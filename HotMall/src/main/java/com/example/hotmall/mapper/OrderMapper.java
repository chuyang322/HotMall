package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hotmall.model.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<UserOrder> {
    @Update("update user_order set state = state +1 where id = ${orderd}")
    public void updateState(Integer orderId);

    @Select("select goods.id,shop_id,goods.price,goods.image,goods.name,goods.info," +
            "order_goods.order_id as orderId,order_goods.number from goods,order_goods " +
            "where goods.id=order_goods.goods_id and goods.shop_id=${shopId}")
    public List<Map<String,Object>> getGoodsInfoByShopId(Integer shopId);

    @Select("select user_order.code,user_order.create_time,user_order.price,user_order.id,user_order.state " +
            "from user_order,goods,order_goods where goods.id=order_goods.goods_id " +
            "and order_goods.order_id=user_order.id and goods.shop_id=${shopId}")
    public List<UserOrder> getMyOrdersByShopId(Integer shopId);
}
