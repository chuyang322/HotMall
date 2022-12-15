package com.example.hotmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName User
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/1 14:12
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String account;
    private String password;
    private String email;
    private Long phone;
    private String gender;
    private Integer age;
    private Integer type;
    private String avatar;
    private Long createTime;
    private Long lastLoginTime;
}
