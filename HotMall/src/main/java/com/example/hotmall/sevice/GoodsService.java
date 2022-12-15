package com.example.hotmall.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hotmall.model.Goods;
import com.example.hotmall.utils.APIResult;

public interface GoodsService extends IService<Goods> {
    public APIResult queryGoodsByKeyword(String keyword, Integer current, Integer size);

    public APIResult goodsList(Integer current, Integer size);

    public APIResult sortList(Integer current, Integer size, String info);

    public APIResult updateStock(Integer id, Integer number);
    public APIResult updateSold(Integer id, Integer number);




}
