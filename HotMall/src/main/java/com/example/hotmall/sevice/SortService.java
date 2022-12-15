package com.example.hotmall.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hotmall.model.Sort;
import com.example.hotmall.utils.APIResult;
import org.springframework.stereotype.Service;


public interface SortService extends IService<Sort> {

    APIResult allSort(Integer current, Integer size);
}
