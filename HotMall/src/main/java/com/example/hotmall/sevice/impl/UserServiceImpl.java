package com.example.hotmall.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import com.example.hotmall.mapper.UserMapper;
import com.example.hotmall.model.User;
import com.example.hotmall.sevice.UserService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.MD5Util;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param user
     * @return 成功，用户基本信息/失败
     */
    @Override
    public APIResult login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("account", user.getAccount());
        try {
            user.setPassword(MD5Util.MD5Password(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return APIResult.createNg("密码加密失败");
        }
        User user1 = userMapper.selectOne(queryWrapper);
        if (user1.getPassword().equals(user.getPassword())) {
            //更新登录时间
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
            updateWrapper.eq("id", user1.getId()).set("last_login_time", System.currentTimeMillis());
            int n = userMapper.update(null, updateWrapper);
            // 设置token
            String token = TokenUtil.getToken(user1.getId(), user1.getPassword());
            // 设置接口返回结果
            user1.setPassword("");
            APIResult apiResult = new APIResult();
            HashMap data = new HashMap<>();
            data.put("user", user1);
            data.put("token", token);
            apiResult.setData(data);
            apiResult.setMessage("登录成功");
            apiResult.setCode(200);
            return apiResult;
        } else {
            return APIResult.createNg("密码错误");
        }
    }

    /**
     * 注册
     *
     * @param user
     * @return 成功/失败
     */
    @Override
    public APIResult register(User user) {
        APIResult apiResult = accountCheck(user.getName());
        if (apiResult.getCode() != 200) {
            return apiResult;
        }
        try {
            user.setPassword(MD5Util.MD5Password(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return APIResult.createNg("密码加密异常");
        }
        user.setCreateTime(System.currentTimeMillis());
        user.setName("用户" + System.currentTimeMillis());
        int n = userMapper.insert(user);
        if (n == 1) {
            return APIResult.createOKMessage("注册成功");
        } else {
            return APIResult.createNg("注册失败");
        }
    }

    @Override
    public APIResult accountCheck(String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return APIResult.createOKMessage("账号可用");
        } else {
            return APIResult.createNg("账号已经注册");
        }
    }

    @Override
    public APIResult applyForStore(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("type").eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return APIResult.createNg("个人商店已经开通");
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId).set("type", 1);
        int n = userMapper.update(null, userUpdateWrapper);
        if (n == 1) {
            return APIResult.createOKMessage("申请成功");
        }
        return APIResult.createNg("申请失败");
    }

    @Override
    public APIResult updateAvatarById(Integer userId, String avatar) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("avatar", avatar);
        int n = userMapper.update(null, userUpdateWrapper);
        if (n != 0) {
            return APIResult.createOKMessage("头像写入数据库成功");
        }
        return APIResult.createNg("头像写入数据库成功");
    }

    @Override
    public APIResult updateUserById(Integer id, User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", id)
                .set("name", user.getName())
                .set("email", user.getEmail())
                .set("phone", user.getPhone())
                .set("avatar", user.getAvatar())
                .set("gender", user.getGender())
                .set("age", user.getAge());
        int n = userMapper.update(null, userUpdateWrapper);
        if(n!=0){
            return APIResult.createOKMessage("修改成功");
        }
        return APIResult.createNg("修改失败");
    }

    @Override
    public APIResult getType(Integer userId){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("id",userId);
        User user=userMapper.selectOne(wrapper);
        APIResult apiResult = new APIResult();
        apiResult.setData(user);
        apiResult.setMessage("登录成功");
        apiResult.setCode(200);
        return apiResult;
    }

}
