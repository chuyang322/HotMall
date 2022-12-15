package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hotmall.mapper.GoodsMapper;
import com.example.hotmall.mapper.ShopMapper;
import com.example.hotmall.mapper.SortMapper;
import com.example.hotmall.model.Goods;
import com.example.hotmall.model.Shop;
import com.example.hotmall.model.Sort;
import com.example.hotmall.sevice.GoodsService;
import com.example.hotmall.sevice.SortService;
import com.example.hotmall.utils.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GoodsServiceImpl
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/4 13:21
 * @Version 1.0
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private SortMapper sortMapper;

    @Override
    public APIResult queryGoodsByKeyword(String keyword, Integer current, Integer size) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>();
            wrapper.like("name", keyword).or().like("info", keyword);
        Page<Goods> page = new Page(current, size);
        IPage<Goods> ipage = goodsMapper.selectPage(page, wrapper);
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(ipage);
        return apiResult;
    }
    @Override
    public APIResult goodsList(Integer current, Integer size) {
        Page<Goods> page = new Page(current, size);
        IPage<Goods> ipage = goodsMapper.selectPage(page,null);
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(ipage);
        return apiResult;
    }


    @Override
    public APIResult sortList(Integer current, Integer size,String info) {
        QueryWrapper<Sort> wrapper=new QueryWrapper();
        wrapper.eq("info",info);
        Sort sort = sortMapper.selectOne(wrapper);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getSortId,sort.getId());
        Page<Goods> page = new Page(current, size);
        IPage<Goods> ipage = goodsMapper.selectPage(page,queryWrapper);
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(ipage);
        return apiResult;
    }

    @Override
    public APIResult updateStock(Integer id,Integer number) {
        Goods goods1=goodsMapper.selectById(id);
        goods1.setStock(goods1.getStock()-number);
        if(goods1.getStock()>=0){
            UpdateWrapper<Goods> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",goods1.getId());
            updateWrapper.set("stock",goods1.getStock());
            int n=goodsMapper.update(null,updateWrapper);
            if(n!=0){
                return APIResult.createOKMessage("更新库存成功");
            }else {
                return APIResult.createNg("更新库存失败");
            }
        }
        return APIResult.createNg("库存不足");
    }
    @Override
    public APIResult updateSold(Integer id,Integer number) {
        Goods goods2=goodsMapper.selectById(id);
        goods2.setSold(goods2.getSold()+number);
        UpdateWrapper<Goods> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",goods2.getId());
        updateWrapper.set("sold",goods2.getSold());
        int n=goodsMapper.update(null,updateWrapper);
        if(n!=0){
            return APIResult.createOKMessage("更新已售成功");
        }else {
            return APIResult.createNg("更新已售失败");
            }
        }
    }
