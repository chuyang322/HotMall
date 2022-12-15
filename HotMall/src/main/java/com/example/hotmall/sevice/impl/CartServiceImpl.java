package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.hotmall.mapper.CartMapper;
import com.example.hotmall.model.Cart;
import com.example.hotmall.sevice.CartService;
import com.example.hotmall.utils.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CartServiceImpl
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/7 12:57
 * @Version 1.0
 **/
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Override
    public APIResult addGoods(Integer userId, Integer goodsId, Integer number) {
        if(queryGoodsByUserIdAndGoodsId(userId,goodsId).getCode()==200){
            return APIResult.createNg("该商品已添加到购物车");
        }
        Cart cart=new Cart();
        cart.setGoodsId(goodsId);
        cart.setUserId(userId);
        cart.setNumber(number);
        int n=cartMapper.insert(cart);
        if (n!=0) {
            return APIResult.createOKMessage("加入购物车成功");
        }else {
            return APIResult.createNg("加入购物车失败");
        }
    }

    @Override
    public APIResult updateGoods(Integer userId, Integer goodsId, Integer number) {
        UpdateWrapper<Cart> cartUpdateWrapper=new UpdateWrapper<>();
        cartUpdateWrapper.eq("user_id",userId).eq("goods_id",goodsId).set("number",number);
        int n=cartMapper.update(null,cartUpdateWrapper);
        if (n!=0){
            return APIResult.createOKMessage("修改成功");
        }
        return APIResult.createNg("修改失败");
    }

    @Override
    public APIResult deleteGoods(Integer userId, Integer goodsId) {
        QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId).eq("goods_id",goodsId);
        int n=cartMapper.delete(cartQueryWrapper);
        if (n!=0){
            return APIResult.createOKMessage("移出购物车成功");
        }
        return APIResult.createNg("移出购物车失败");
    }

    @Override
    public APIResult queryGoodsByUserId(Integer userId) {
        List<Map<String,Object>> list=cartMapper.queryGoodsByUserId(userId);
        if(list!=null){
            return APIResult.createOk(list);
        }else {
            return APIResult.createNg("查询失败");
        }
    }

    @Override
    public APIResult queryGoodsByUserIdAndGoodsId(Integer userId, Integer goodsId) {
        QueryWrapper<Cart> cartQueryWrapper=new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId).eq("goods_id",goodsId);
        Cart cart=cartMapper.selectOne(cartQueryWrapper);
        if(cart!=null){
            return APIResult.createOk(cart);
        }
        return APIResult.createNg("查询失败");
    }
}
