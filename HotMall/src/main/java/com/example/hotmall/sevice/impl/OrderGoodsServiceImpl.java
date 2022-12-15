package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.hotmall.mapper.OrderGoodsMapper;
import com.example.hotmall.model.Goods;
import com.example.hotmall.model.OrderGoods;
import com.example.hotmall.sevice.GoodsService;
import com.example.hotmall.sevice.OrderGoodsService;
import com.example.hotmall.utils.APIResult;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private GoodsService goodsService;


    @Override
    public APIResult addGoodsToOrder(List<Map<String, Integer>> list, Integer orderId) {
        Long totalPrice = Long.valueOf(0);
        for (Map<String, Integer> map : list) {
            System.out.println(map);
            if (goodsService.updateStock(map.get("id"), map.get("number")).getCode() != 200) {
                return APIResult.createNg("更新库存失败");
            }
            if( goodsService.updateSold(map.get("id"),map.get("number")).getCode()!=200){
                return APIResult.createNg("更新已售失败");
            }
            Goods goods=goodsService.getById(map.get("id"));
            if (goods==null){
                return APIResult.createNg("计算总价失败");
            }
            totalPrice += map.get("number") * goods.getPrice();
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoodsId(map.get("id"));
            orderGoods.setOrderId(orderId);
            orderGoods.setNumber(map.get("number"));
            int n = orderGoodsMapper.insert(orderGoods);
            if (n == 0) {
                return APIResult.createNg("商品添加订单失败");
            }
        }
        return APIResult.createOk(totalPrice);
    }

    @Override
    public List<Map<String, Object>> getOrderGoodsInfoByOrderId(Integer id) {
        List<Map<String, Object>> list = orderGoodsMapper.queryOrderGoodsInfo(id);
        return list;
    }

    @Override
    public APIResult confirmReceipt(Integer userId, Integer orderGoodsId) {
        if (userId != orderGoodsMapper.queryUserIdByOrderGoodsId(orderGoodsId)) {
            return APIResult.createNg("用户不匹配");
        }
        UpdateWrapper<OrderGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", orderGoodsId).set("state", 2);
        int n = orderGoodsMapper.update(null, updateWrapper);
        if (n != 0) {
            return APIResult.createOKMessage("已确认收货");
        }
        return APIResult.createNg("确认收货失败");
    }
}
