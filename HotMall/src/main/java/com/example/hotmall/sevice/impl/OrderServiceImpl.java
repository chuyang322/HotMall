package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.hotmall.mapper.OrderMapper;
import com.example.hotmall.model.UserOrder;
import com.example.hotmall.sevice.OrderService;
import com.example.hotmall.utils.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public APIResult createOrder(Integer userId, Integer addressId) {
        UserOrder order = new UserOrder();
        order.setUserId(userId);
        Random random = new Random();
        order.setCode("HT" + System.currentTimeMillis() + random.nextInt(1000));
        order.setAddressId(addressId);
        order.setCreateTime(System.currentTimeMillis());
        int n = orderMapper.insert(order);
        if (n != 0) {
            UserOrder order1 = orderMapper.selectOne(new QueryWrapper<UserOrder>().eq("code", order.getCode()));
            if (order1 != null)
                return APIResult.createOk(order1.getId());
        }
        return APIResult.createNg("生成订单失败");
    }

    @Override
    public APIResult setPrice(Integer id, Long price) {
        UpdateWrapper<UserOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("price", price);
        int n = orderMapper.update(null, updateWrapper);
        if (n != 0) {
            return APIResult.createOKMessage("设置总价成功");
        }
        return APIResult.createNg("设置总价失败");
    }

    @Override
    public List<UserOrder> getOrderListByUserId(Integer id) {
        QueryWrapper<UserOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<UserOrder> list = orderMapper.selectList(queryWrapper);
        Collections.reverse(list);
        return list;
    }
    @Override
    public List<UserOrder> getOrderListByShopId(Integer shopId) {
        List<UserOrder> list = orderMapper.getMyOrdersByShopId(shopId);
        Collections.reverse(list);
        return list;
    }

    @Override
    public APIResult updateOrder(Integer orderId){
        orderMapper.updateState(orderId);
        return APIResult.createOKMessage("更新成功");
    }
}
