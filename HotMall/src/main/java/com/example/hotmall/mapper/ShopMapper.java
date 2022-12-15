package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.example.hotmall.model.Shop;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    @Select("select goods.name,goods.info,goods.price,goods.sold,goods.stock,goods.image,shop.id as shopId,shop.name as shopName,shop.info as shopInfo from shop,goods " +
            "where shop.id=goods.shop_id and shop.user_id=${userId} order by goods.id desc limit ${current},${pageSize}")
   public List<Map<String,Object>> queryGoodsByShopIdAndUserId(Integer userId,Integer current, Integer pageSize);
}
