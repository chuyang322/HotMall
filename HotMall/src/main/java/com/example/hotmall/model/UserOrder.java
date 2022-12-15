package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String code;
    private Long createTime;
    private Long payTime;
    private Integer userId;
    private Long price;
    private Integer state;
    private Integer addressId;
}
