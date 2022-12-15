package com.example.hotmall.controller;

import com.example.hotmall.model.Address;
import com.example.hotmall.sevice.AddressService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public APIResult getAddressByUserId(HttpServletRequest request){
        return addressService.queryAddressByUserId(TokenUtil.getUserId(request.getHeader("token")));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public APIResult addAddressByUserId(@RequestBody Address address,HttpServletRequest request){
        return addressService.addAddressByUserId(TokenUtil.getUserId(request.getHeader("token")),address);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public APIResult updateAddressByUserId(@RequestBody Address address, HttpServletRequest request){
        return addressService.updateAddressByUserId(TokenUtil.getUserId(request.getHeader("token")),address);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public APIResult deleteAddressById(@PathVariable("id") Integer addressId,HttpServletRequest request){
        return addressService.deleteAddressById(TokenUtil.getUserId(request.getHeader("token")),addressId);
    }
}
