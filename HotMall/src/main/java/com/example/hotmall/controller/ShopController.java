package com.example.hotmall.controller;

import com.example.hotmall.model.DTO.DeleteDTO;
import com.example.hotmall.model.Goods;
import com.example.hotmall.model.Shop;
import com.example.hotmall.sevice.GoodsService;
import com.example.hotmall.sevice.ShopService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/setUpShop",method = RequestMethod.POST)
    public APIResult setUpShop(@RequestBody Shop shop, HttpServletRequest request){
        shop.setUserId(TokenUtil.getUserId(request.getHeader("token")));
        return shopService.setUpShop(shop);
    }

    @RequestMapping(value = "/list/{current}/{size}", method = RequestMethod.GET)
    public APIResult getMyGoodsList(HttpServletRequest request, @PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        return shopService.getMyGoodsList(TokenUtil.getUserId(request.getHeader("token")), current, size);
    }
    @RequestMapping(value = "/list/{current}/{size}/{keyword}", method = RequestMethod.GET)
    public APIResult getMyGoodsByKeyword(HttpServletRequest request, @PathVariable("current") Integer current, @PathVariable("size") Integer size, @PathVariable("keyword") String keyword) {
        return shopService.getMyGoodsByKeyword(TokenUtil.getUserId(request.getHeader("token")), current, size,keyword);
    }
    @RequestMapping(value = "/myShop", method = RequestMethod.GET)
    public APIResult getMyShop(HttpServletRequest request) {
        return shopService.getMyShop(TokenUtil.getUserId(request.getHeader("token")));
    }
    /**
     * 删除商品
     */
    @RequestMapping(value = "/deleteById/{id}",method = RequestMethod.GET)
    public APIResult deleteGoodsById(@PathVariable("id") Integer id){
        APIResult apiResult=shopService.deleteGoodsById(id);
        return apiResult;
    }
    /**
     * 删除选中商品
     */
    @PostMapping("deleteByIds")
    public APIResult deleteGoodsByIds(@RequestBody DeleteDTO deleteDTO){
        boolean b = goodsService.removeByIds(deleteDTO.getIds());
        return APIResult.createOk(b);
    }

    /**
     * 添加商品
     */
    @RequestMapping("/saveGoods")
    @PostMapping(value = "saveGoods",produces = "application/json;charset=UTF-8")
    public APIResult saveGoods(@RequestBody Goods goods,HttpServletRequest request){
        return shopService.saveGoods(goods,TokenUtil.getUserId(request.getHeader("token")));
    }

    /**
     * 修改商品
     */
    @RequestMapping("/updateGoods")
    @PostMapping(value = "updateGoods",produces = "application/json;charset=UTF-8")
    public APIResult updateGoods(@RequestBody Goods goods){
        boolean b=goodsService.updateById(goods);
        return APIResult.createOk(b);
    }


    /**
     * @return商品
     */
    @RequestMapping(value = "/setUpGoods/{shopId}",method = RequestMethod.POST)
    public APIResult setUpGoods(@PathVariable("shopId") Integer shopId){
        return shopService.setUpGoods(shopId);
    }
}
