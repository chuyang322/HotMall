package com.example.hotmall.sevice;

import com.example.hotmall.utils.APIResult;

public interface CartService {
    public APIResult addGoods(Integer userId,Integer goodsId,Integer number);
    public APIResult updateGoods(Integer userId,Integer goodsId,Integer number);
    public APIResult deleteGoods(Integer userId,Integer goodsId);
    public APIResult queryGoodsByUserId(Integer userId);
    public APIResult queryGoodsByUserIdAndGoodsId(Integer userId,Integer goodsId);
}
