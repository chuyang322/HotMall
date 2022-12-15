package com.example.hotmall.sevice;

import com.example.hotmall.model.User;
import com.example.hotmall.utils.APIResult;

public interface UserService {
    public APIResult login(User user);
    public APIResult register(User user);
    public APIResult accountCheck(String account);
    public APIResult applyForStore(Integer userId);
    public APIResult updateAvatarById(Integer userId, String avatar);
    public APIResult updateUserById(Integer id,User user);
    APIResult getType(Integer token);
}
