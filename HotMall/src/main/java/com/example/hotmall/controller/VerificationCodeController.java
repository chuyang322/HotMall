package com.example.hotmall.controller;

import com.example.hotmall.model.VerificationCode;
import com.example.hotmall.utils.APIResult;
import com.example.hotmall.utils.VerificationCodeFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/verification")
public class VerificationCodeController {
    @RequestMapping(value="/code")
    public APIResult verificationCode(HttpSession session, HttpServletResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String code = VerificationCodeFactory.create(60, 30, "jpg", outputStream);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setLifeTime((long) (60 * 1000));
        verificationCode.setCreateTime(System.currentTimeMillis());
        session.setAttribute("verificationCode", verificationCode);
        try {
            ServletOutputStream out = response.getOutputStream();
            outputStream.writeTo(out);
            return APIResult.createOKMessage("获取验证码成功");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return APIResult.createNg("获取图片失败");
        }
    }

    /**
     * 校验验证码
     * @param session
     * @param verificationCode
     * @return
     */
    @RequestMapping(value="/check/{verificationCode}",method = RequestMethod.GET)
    public APIResult checkVerificationCode(HttpSession session, @PathVariable("verificationCode") String verificationCode) {
        // 当前时间戳
        long l = System.currentTimeMillis();
        VerificationCode sCode = (VerificationCode) session.getAttribute("verificationCode");
        if (l - sCode.getCreateTime() < sCode.getLifeTime()) {
            if (verificationCode.equalsIgnoreCase(sCode.getCode()) && verificationCode != null && verificationCode != "") {

                return APIResult.createOKMessage("验证码正确！");
            } else {

                return APIResult.createNg("验证码错误！");
            }
        } else {

            return APIResult.createNg("验证码失效！");
        }
    }
}
