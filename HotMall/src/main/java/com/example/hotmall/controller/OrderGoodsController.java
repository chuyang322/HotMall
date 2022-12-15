package com.example.hotmall.controller;

import com.example.hotmall.sevice.OrderGoodsService;
import com.example.hotmall.sevice.OrderService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orderGoods")
public class OrderGoodsController {
    @Autowired
    private OrderGoodsService orderGoodsService;

    @RequestMapping(value = "/confirmReceipt/{id}",method = RequestMethod.GET)
    public APIResult confirmReceipt(@PathVariable("id")Integer id, HttpServletRequest request){
        Integer userId= TokenUtil.getUserId(request.getHeader("token"));
        return orderGoodsService.confirmReceipt(userId,id);
    }
}
