package com.example.hotmall.sevice;


import com.example.hotmall.model.Address;
import com.example.hotmall.utils.APIResult;

import java.util.List;
import java.util.Map;

public interface AddressService {
    public APIResult addAddressByUserId(Integer userId, Address address);
    public APIResult updateAddressByUserId(Integer userId, Address address);
    public APIResult deleteAddressById(Integer userId,Integer addressId);
    public APIResult queryAddressByUserId(Integer userId);

    public List<Map<String,Object>> getAddressByAddressId(Integer id);
}
