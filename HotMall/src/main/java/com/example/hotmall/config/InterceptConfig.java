package com.example.hotmall.config;

import com.example.hotmall.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new JwtInterceptor())
                //拦截的路径 需要进行token验证的路径
                .addPathPatterns("/user/**","/shop/**","/hello/interceptor","/cart/**","/order/**","/address/**","/cart/**","/orderGoods/**")
                //放行的路径
                .excludePathPatterns("/hello","/user/login","/user/register");
    }
}
