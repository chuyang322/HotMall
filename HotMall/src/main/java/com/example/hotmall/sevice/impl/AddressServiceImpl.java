package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.hotmall.mapper.AddressMapper;
import com.example.hotmall.model.Address;
import com.example.hotmall.sevice.AddressService;
import com.example.hotmall.utils.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public APIResult addAddressByUserId(Integer userId, Address address) {
        address.setUserId(userId);
        int n = addressMapper.insert(address);
        if (n != 0) {
            return APIResult.createOKMessage("添加地址成功");
        }
        return APIResult.createNg("添加地址失败");
    }

    @Override
    public APIResult updateAddressByUserId(Integer userId, Address address) {
        UpdateWrapper<Address> addressUpdateWrapper = new UpdateWrapper<>();
        addressUpdateWrapper.eq("user_id", userId).eq("id",address.getId()).set("name",address.getName()).set("phone",address.getPhone()).set("info",address.getInfo());
        int n = addressMapper.update(null, addressUpdateWrapper);
        if (n != 0) {
            return APIResult.createOKMessage("修改收货地址成功");
        }
        return APIResult.createNg("修改收货地址失败");
    }

    @Override
    public APIResult deleteAddressById(Integer userId, Integer addressId) {
        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.eq("user_id", userId).eq("id", addressId);
        int n = addressMapper.delete(addressQueryWrapper);
        if (n != 0) {
            return APIResult.createOKMessage("删除收货地址成功");
        }
        return APIResult.createNg("删除收货地址失败");
    }

    @Override
    public APIResult queryAddressByUserId(Integer userId) {
        QueryWrapper<Address> addressQueryWrapper=new QueryWrapper<>();
        addressQueryWrapper.eq("user_id",userId);
        List<Address> list=addressMapper.selectList(addressQueryWrapper);
        if (list!=null){
            return APIResult.createOk(list);
        }
        return APIResult.createNg("获取收货地址列表失败");
    }
    @Override
    public List<Map<String,Object>> getAddressByAddressId(Integer id) {
        List<Map<String,Object>> list=addressMapper.queryAddress(id);
        return list;
    }
}
