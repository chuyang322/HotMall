package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Cart
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/1 15:54
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer goodsId;
    private Integer number;
}
