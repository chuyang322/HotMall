package com.example.hotmall.controller;

import com.example.hotmall.model.Goods;
import com.example.hotmall.sevice.GoodsService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    @RequestMapping(value = "/list/{current}/{size}/{keyword}", method = RequestMethod.GET)
    public APIResult queryGoodsByKeyword(@PathVariable("keyword") String keyword, @PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        return goodsService.queryGoodsByKeyword(keyword, current, size);
    }

    @RequestMapping(value = "/list/{current}/{size}", method = RequestMethod.GET)
    public APIResult goodsList(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        return goodsService.goodsList(current, size);
    }
    @RequestMapping(value = "sort/{current}/{size}/{sort}", method = RequestMethod.GET)
    public APIResult sortList(@PathVariable("current") Integer current, @PathVariable("size") Integer size,@PathVariable("sort") String sort) {
        return goodsService.sortList(current, size,sort);
    }

//    @PostMapping("update")
//    public APIResult updateGoods(@RequestBody Goods goods){
//        boolean b=goodsService.updateById(goods);
//        return APIResult.createOk(b);
//    }
}
