package com.example.hotmall.interceptor;

import com.example.hotmall.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, String> map=new HashMap<>();
        //从http请求头获取token
        String token = request.getHeader("token");

        // 1、检查 token
        if (token == null || "".equals(token)) {
            System.out.println("拦截"+request.getRequestURI());
            return false;
        }

        // 2、验证 token
        if (!TokenUtil.verify(token)) {
            System.out.println("拦截"+request.getRequestURI());
            return false;
        }

        return true;
    }

}
