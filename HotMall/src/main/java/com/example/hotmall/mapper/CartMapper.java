package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hotmall.model.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    @Select("select cart.id as id,cart.number,goods.id as goodsId,goods.name,goods.info,goods.price,goods.sold,goods.stock,goods.image from cart,goods where cart.goods_id=goods.id and cart.user_id=${userId} order by cart.id desc")
    public List<Map<String,Object>> queryGoodsByUserId(Integer userId);
}
