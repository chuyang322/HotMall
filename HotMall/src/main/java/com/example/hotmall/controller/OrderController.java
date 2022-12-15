package com.example.hotmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hotmall.mapper.AddressMapper;
import com.example.hotmall.mapper.GoodsMapper;
import com.example.hotmall.mapper.OrderMapper;
import com.example.hotmall.mapper.ShopMapper;
import com.example.hotmall.model.*;
import com.example.hotmall.sevice.AddressService;
import com.example.hotmall.sevice.OrderGoodsService;
import com.example.hotmall.sevice.OrderService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.MapUtil;
import com.example.hotmall.utils.TokenUtil;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderController
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/7 18:11
 * @Version 1.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public APIResult createOrder(@RequestBody() List<Map<String, Integer>> list, @RequestParam("addressId") Integer addressId, HttpServletRequest request) {
        Integer userId = TokenUtil.getUserId(request.getHeader("token"));
        APIResult apiResult = orderService.createOrder(userId, addressId);
        if (apiResult.getCode() == 200) {
            APIResult apiResult1=orderGoodsService.addGoodsToOrder(list, (Integer) apiResult.getData());
            if (apiResult1.getCode() == 200) {
                if (orderService.setPrice((Integer) apiResult.getData(), (Long) apiResult1.getData()).getCode()==200) {
                    return APIResult.createOKMessage("下单成功！");
                }
            }
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return APIResult.createNg("下单失败");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResult queryOrderListByUserId(HttpServletRequest request) {
        Integer userId = TokenUtil.getUserId(request.getHeader("token"));
        List<UserOrder> list=orderService.getOrderListByUserId(userId);
        List<Map<String,Object>> list1=new ArrayList<>();
        if(list==null) return APIResult.createNg("查询订单列表失败");
        for (UserOrder order:list) {
            List list2=orderGoodsService.getOrderGoodsInfoByOrderId(order.getId());
            if (list2==null) return APIResult.createNg("查询订单商品失败");
            List list3 =addressService.getAddressByAddressId(order.getAddressId());
            Map<String,Object> map= MapUtil.objectToMap(order);
            map.put("goods",list2);
            map.put("address",list3);
            list1.add(map);
        }
        return APIResult.createOk(list1);
    }
    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    public APIResult getMyOrdersByUserId(HttpServletRequest request) {
        Integer userId = TokenUtil.getUserId(request.getHeader("token"));
        QueryWrapper<Shop> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        Shop shop = shopMapper.selectOne(wrapper);
        Integer shopId=shop.getId();
        List<UserOrder> list = orderService.getOrderListByShopId(shopId);
        List<Map<String,Object>> list1=new ArrayList<>();
        if(list==null) return APIResult.createNg("查询订单列表失败");
        for (UserOrder order:list) {
            List list2 = orderGoodsService.getOrderGoodsInfoByOrderId(order.getId());
            if (list2==null) return APIResult.createNg("查询订单商品失败");
            Map<String,Object> map= MapUtil.objectToMap(order);
            map.put("goods",list2);
            list1.add(map);
        }
        return APIResult.createOk(list1);
    }

    @RequestMapping(value = "/updateState/{orderId}",method = RequestMethod.GET)
    public APIResult updateOrder(@PathVariable("orderId") Integer orderId){
        return orderService.updateOrder(orderId);
    }
}
