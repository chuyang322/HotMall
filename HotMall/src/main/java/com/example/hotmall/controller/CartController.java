package com.example.hotmall.controller;

import com.example.hotmall.sevice.CartService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/add/{goodsId}/{number}",method = RequestMethod.GET)
    public APIResult addGoods(@PathVariable("goodsId") Integer goodsId, @PathVariable("number") Integer number, HttpServletRequest request) {
        return cartService.addGoods(TokenUtil.getUserId(request.getHeader("token")), goodsId, number);
    }

    @RequestMapping(value = "/update/{goodsId}/{number}",method = RequestMethod.GET)
    public APIResult updateGoods(@PathVariable("goodsId") Integer goodsId, @PathVariable("number") Integer number, HttpServletRequest request) {
        return cartService.updateGoods(TokenUtil.getUserId(request.getHeader("token")), goodsId, number);
    }

    @RequestMapping(value = "/delete/{goodsId}",method = RequestMethod.GET)
    public APIResult deleteGoods(@PathVariable("goodsId") Integer goodsId,HttpServletRequest request) {
        return cartService.deleteGoods(TokenUtil.getUserId(request.getHeader("token")), goodsId);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public APIResult getGoodsByUserId(HttpServletRequest request) {
        return cartService.queryGoodsByUserId(TokenUtil.getUserId(request.getHeader("token")));
    }
}
