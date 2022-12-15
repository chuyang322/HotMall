package com.example.hotmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hotmall.model.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    @Select("select name,phone,info from address where id=${id}")
    public List<Map<String,Object>> queryAddress(Integer id);
}
