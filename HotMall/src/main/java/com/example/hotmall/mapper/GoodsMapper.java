package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.example.hotmall.model.Goods;


import java.util.List;
import java.util.Map;


@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select good.id as id,cart.number,goods.id as goodsId,goods.name,goods.info,goods.price from cart,goods where cart.goods_id=goods.id and cart.user_id=${userId} order by cart.id desc")
    public List<Map<String,Object>> queryGoodsByShopId(Integer shopId);
}
