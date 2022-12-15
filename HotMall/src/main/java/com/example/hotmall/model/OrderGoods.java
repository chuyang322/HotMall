package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoods {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer goodsId;
    private Integer number;
    private Integer state;
}
