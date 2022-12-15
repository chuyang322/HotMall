package com.example.hotmall.sevice;

import com.example.hotmall.utils.APIResult;

import java.util.List;
import java.util.Map;

public interface OrderGoodsService {
    public APIResult addGoodsToOrder(List<Map<String,Integer>> list,Integer orderId);
    public List<Map<String,Object>> getOrderGoodsInfoByOrderId(Integer id);
    public APIResult confirmReceipt(Integer userId,Integer orderGoodsId);
}
