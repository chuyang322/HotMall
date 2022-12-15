package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Shop
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/1 15:36
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer userId;
    private String info;
    private Long create_time;
    private String password;
}
