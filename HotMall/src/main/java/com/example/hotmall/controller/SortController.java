package com.example.hotmall.controller;

import com.example.hotmall.mapper.SortMapper;
import com.example.hotmall.model.Sort;
import com.example.hotmall.sevice.SortService;
import com.example.hotmall.utils.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sort")
public class SortController {
    @Autowired
    private SortService sortService;


    @RequestMapping(value = "/allSort/{current}/{size}", method = RequestMethod.GET)
    public APIResult allSort(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        return sortService.allSort(current, size);
    }


}
