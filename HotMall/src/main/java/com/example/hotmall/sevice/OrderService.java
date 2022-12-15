package com.example.hotmall.sevice;

import com.example.hotmall.model.UserOrder;
import com.example.hotmall.utils.APIResult;

import java.util.List;

public interface OrderService {
    public APIResult createOrder(Integer userId,Integer addressId);
    public APIResult setPrice(Integer id,Long price);
    public List<UserOrder> getOrderListByUserId(Integer id);
     public List<UserOrder> getOrderListByShopId(Integer shopId);
    APIResult updateOrder(Integer orderId);
}
