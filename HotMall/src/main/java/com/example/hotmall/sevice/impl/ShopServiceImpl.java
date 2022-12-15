package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hotmall.mapper.GoodsMapper;
import com.example.hotmall.mapper.ShopMapper;
import com.example.hotmall.mapper.SortMapper;
import com.example.hotmall.mapper.UserMapper;
import com.example.hotmall.model.Cart;
import com.example.hotmall.model.Goods;
import com.example.hotmall.model.Shop;
import com.example.hotmall.sevice.ShopService;
import com.example.hotmall.sevice.SortService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private GoodsMapper goodsMapper;

//    @Autowired(required = false)
//    private SortService sortService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SortMapper sortMapper;

    @Override
    public APIResult setUpShop(Shop shop) {
        QueryWrapper<Shop> shopQueryWrapper=new QueryWrapper<>();
        shopQueryWrapper.eq("user_id",shop.getUserId());
        Shop shop1=shopMapper.selectOne(shopQueryWrapper);
        if(shop1!=null){
            return APIResult.createNg("已经开启了店铺");
        }
        shop.setCreate_time(System.currentTimeMillis());
        try {
            shop.setPassword(MD5Util.MD5Password(shop.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return APIResult.createNg("密码加密失败");
        }
        int n=shopMapper.insert(shop);
        if(n==1){
            userMapper.updateType(shop.getUserId());
            return APIResult.createOKMessage("开店成功");
        }
        return APIResult.createNg("开店失败");
    }
    @Override
    public APIResult getMyGoodsList(Integer userId,Integer current, Integer size) {
       QueryWrapper<Shop> wrapper=new QueryWrapper<>();
       wrapper.eq("user_id",userId);
       Shop shop=shopMapper.selectOne(wrapper);
        LambdaQueryWrapper<Goods> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getShopId,shop.getId());
        Page<Goods> page = new Page(current, size);
        IPage<Goods> ipage = goodsMapper.selectPage(page,queryWrapper);
        ipage.getRecords().stream().forEach(x->x.setSortInfo(sortMapper.selectById(x.getSortId())));
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(ipage);
        return apiResult;
    }
    @Override
    public APIResult getMyGoodsByKeyword(Integer userId,Integer current, Integer size,String keyword) {
        QueryWrapper<Shop> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        Shop shop=shopMapper.selectOne(wrapper);
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("shop_id",shop.getId()).like("name", keyword).or().like("info", keyword);
        Page<Goods> page = new Page(current, size);
        IPage<Goods> ipage = goodsMapper.selectPage(page,queryWrapper);
        ipage.getRecords().stream().forEach(x->x.setSortInfo(sortMapper.selectById(x.getSortId())));
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(ipage);
        return apiResult;
    }
    @Override
    public APIResult getMyShop(Integer userId) {
       QueryWrapper<Shop> wrapper=new QueryWrapper<>();
       wrapper.eq("user_id",userId);
        Shop shop = shopMapper.selectOne(wrapper);
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(shop);
        return apiResult;
    }
    @Override
    public APIResult deleteGoodsById(Integer goodsId) {
//        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
//        wrapper.eq("id",goodsId);
        goodsMapper.deleteById(goodsId);
        return APIResult.createOKMessage("删除成功");
    }
//    @Override
//    public APIResult deleteGoodsByIds(List ids){
//        goodsMapper.deleteBatchIds(ids);
//        return APIResult.createOKMessage("删除成功");
//    }
    @Override
    public APIResult setUpGoods(Integer shopId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id",shopId).eq("state","1");
        goodsMapper.selectList(queryWrapper);
        return APIResult.createOKMessage("添加成功");
    }
    @Override
    public APIResult saveGoods(Goods goods,Integer id) {
        Shop shop=shopMapper.selectOne(new QueryWrapper<Shop>().eq("user_id",id));
        if(shop!=null){
            goods.setShopId(shop.getId());
            int n=goodsMapper.insert(goods);
            if(n!=0){
                return APIResult.createOKMessage("添加成功");
            }
        }
        return APIResult.createNg("添加失败");
    }



    @Override
    public APIResult updateGoods(Goods goods) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getName,goods.getName()).eq(Goods::getPrice,goods.getPrice()).eq(Goods::getStock,goods.getStock()).eq(Goods::getState,goods.getState());
        goodsMapper.update(goods,queryWrapper);
        return APIResult.createOKMessage("修改成功");
    }

}
