package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hotmall.mapper.SortMapper;
import com.example.hotmall.model.Sort;
import com.example.hotmall.sevice.SortService;
import com.example.hotmall.utils.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements SortService {
    @Autowired
    private SortMapper sortMapper;

    @Override
    public APIResult allSort(Integer current, Integer size) {
        Page<Sort> page = new Page(current, size);
        IPage<Sort> ipage = sortMapper.selectPage(page,null);
        APIResult apiResult = new APIResult();
        apiResult.setCode(200);
        apiResult.setMessage("查询成功");
        apiResult.setData(ipage);
        return apiResult;
    }
}
