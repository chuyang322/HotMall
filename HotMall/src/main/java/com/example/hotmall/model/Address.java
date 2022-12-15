package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Address
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/1 15:32
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Long phone;
    private String info;
    private Integer userId;
    @TableLogic()
    private boolean deleted;
}
