package com.example.hotmall.controller;

import com.example.hotmall.model.User;
import com.example.hotmall.sevice.UserService;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return user基本信息
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public APIResult login(@RequestBody User user){
        APIResult apiResult=userService.login(user);
        return apiResult;
    }

    /**
     * 注册
     * @param user
     * @return 成功/失败
     */
    @RequestMapping(value="/register",method = RequestMethod.POST)
    public APIResult register(@RequestBody User user){
        System.out.println(user);
        APIResult apiResult=userService.register(user);
        return apiResult;
    }

    /**
     * 检查账号是否已经被注册
     * @param account
     * @return 可用/已被注册
     */
    @RequestMapping(value = "/accountCheck/{account}",method = RequestMethod.GET)
    public APIResult nameCheck(@PathVariable("account") String account) {
        APIResult apiResult = userService.accountCheck(account);
        return apiResult;
    }

    /**
     * 申请开店
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyForStore",method = RequestMethod.GET)
    public APIResult applyForStore(HttpServletRequest request){
        return userService.applyForStore(TokenUtil.getUserId(request.getHeader("token")));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public APIResult updateUser(@RequestBody User user,HttpServletRequest request){
        return userService.updateUserById(TokenUtil.getUserId(request.getHeader("token")),user);
    }
    @RequestMapping(value = "/getType",method = RequestMethod.GET)
    public APIResult getType(HttpServletRequest request){
        return userService.getType(TokenUtil.getUserId(request.getHeader("token")));
    }
}
