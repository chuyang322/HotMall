package com.example.hotmall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Verification
 * @Description TODO:
 * @Author chuyang
 * @Date 2022/12/2 15:51
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {
    private String code;
    // 时间戳
    private Long createTime;
    // 有效时间（毫秒）
    private Long lifeTime;
}
