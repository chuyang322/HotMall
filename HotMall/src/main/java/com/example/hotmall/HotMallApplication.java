package com.example.hotmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.hotmall.mapper")
@SpringBootApplication
public class HotMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotMallApplication.class, args);
    }

}
