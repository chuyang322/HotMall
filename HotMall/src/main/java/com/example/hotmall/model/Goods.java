package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Date createTime;
    private Integer state;
    private Integer shopId;
    private Integer stock;
    private Integer sold;
    @TableLogic()
    private boolean deleted;
    private Long price;
    private String image;
    private String name;
    private String info;
    private Integer sortId;

    @TableField(exist = false)
    private Sort sortInfo;
}
