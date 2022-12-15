package com.example.hotmall.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hotmall.model.Goods;
import com.example.hotmall.model.Shop;
import com.example.hotmall.utils.APIResult;

import java.util.List;


public interface ShopService {
    public APIResult setUpShop(Shop shop);

    public APIResult setUpGoods(Integer shopId);

    public APIResult getMyGoodsList(Integer userId, Integer current, Integer size);

    public APIResult getMyGoodsByKeyword(Integer userId, Integer current, Integer size, String keyword);

    public APIResult getMyShop(Integer userId);

    public APIResult deleteGoodsById(Integer goodsId);

//   public APIResult deleteGoodsByIds(List ids);
    APIResult saveGoods(Goods goods,Integer id);

    APIResult updateGoods(Goods goods);
}
